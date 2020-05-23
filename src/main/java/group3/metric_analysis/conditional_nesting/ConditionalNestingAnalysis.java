package group3.metric_analysis.conditional_nesting;

import group3.MetricAnalysis;
import org.apache.commons.lang3.ObjectUtils;
import spoon.Launcher;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.*;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.InvocationFilter;
import spoon.reflect.visitor.filter.TypeFilter;
import spoon.support.reflect.code.CtBlockImpl;

import java.util.*;


public class ConditionalNestingAnalysis extends MetricAnalysis {
    private HashMap<String, HashMap<String, Integer>> classConditionalNestingScores;

    public ConditionalNestingAnalysis() {
        classConditionalNestingScores = new HashMap<String, HashMap<String, Integer>>();
    }

    public HashMap<String, HashMap<String, Integer>> getClassConditionalNestingScores() {
        return classConditionalNestingScores;
    }

    public void performAnalysis (Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, Integer> methodConditionalNestingScores = new HashMap<String, Integer>();
            for (CtExecutable<?> methodObject : getMethods(classObject)) {
                int maxDepth = getIfStatementDepthForMethod(methodObject);
                methodConditionalNestingScores.put(methodObject.getSignature(), maxDepth);
            }
            classConditionalNestingScores.put(classObject.getQualifiedName(), methodConditionalNestingScores);
        }
    }

    public static int getIfStatementDepthForMethod(CtExecutable<?> methodObject) {
        int maxDepth = 0;
        for (CtIf ifStatement : Query.getElements(methodObject, new TypeFilter<CtIf>(CtIf.class))) {
            if(ifStatement.getParent().getRoleInParent() == CtRole.BODY) {
                CtBlock codeBlock = ifStatement.getThenStatement();
                int currentDepth = getIfs(codeBlock, 1);
                if (currentDepth > maxDepth){
                    maxDepth = currentDepth;
                }
                CtBlock elseCodeBlock = getElseStatement(ifStatement);
                int maxDepthElse = getIfs(elseCodeBlock, 1);
                if (Math.max(currentDepth,  maxDepthElse) > maxDepth) {
                    maxDepth = Math.max(currentDepth, maxDepthElse);
                }
            }
        }
        return maxDepth;
    }

    public static int getIfs(CtBlock currentCodeBlock, int maxDepth) {
        int currentDepth = maxDepth+1;
        if (currentCodeBlock != null) {
            for (CtIf ifStatement : Query.getElements(currentCodeBlock, new TypeFilter<CtIf>(CtIf.class))) {

                CtBlock codeBlock = ifStatement.getThenStatement();
                currentDepth = getIfs(codeBlock, currentDepth);
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                }
            }
            Collection<CtIf> ifStatementsCollection = Query.getElements(currentCodeBlock, new TypeFilter<CtIf>(CtIf.class));
            ArrayList<CtIf> ifStatements = new ArrayList<CtIf>(ifStatementsCollection);
            if (ifStatements.size() > 0) {
                CtBlock elseStatementBlock = getElseStatement(ifStatements.get(0));
                currentDepth = getIfs(elseStatementBlock, currentDepth);
                if (currentDepth > maxDepth) {
                    maxDepth = currentDepth;
                }
            }
        }
        return maxDepth;
    }
    public static CtBlock getElseStatement(CtIf ifStatement) {
        CtBlock elseBlock = null;
        CtBlock nextElseBlock = null;
        boolean running = true;
        while(running) {
            try {
                nextElseBlock = ifStatement.getElseStatement();
                if(nextElseBlock == null) {
                    running = false;
                } else {
                    elseBlock = nextElseBlock;
                    ifStatement = Query.getElements(nextElseBlock, new TypeFilter<CtIf>(CtIf.class)).get(0);
                }
            } catch (Exception e) {
                running = false;
            }
        }
        return elseBlock;
    }
//    Collection<CtIf> ifStatements = Query.getElements(elseBlock, new TypeFilter<CtIf>(CtIf.class));
//    ArrayList<CtIf> methodsArrayList = new ArrayList<CtIf>(ifStatements);


    //    public static void getIfs(CtIf ifStatement) {
//        System.out.println(ifStatement.getThenStatement().toString());
//        CtIf nextIfStatement = null;
//        CtBlockImpl<?> elseStatement = null;
//        try {
//            nextIfStatement = ifStatement.getElseStatement().getElements(new TypeFilter<CtIf>(CtIf.class)).get(0);
//        } catch (Exception e) {
//            elseStatement = ifStatement.getElseStatement();
//        }
//        if (nextIfStatement != null) {
//            getIfs(nextIfStatement);
//        }
//        if (elseStatement != null) {
//            System.out.println(elseStatement);
//        }
//    }
//    public static void scanIfStatementBlock(CtBlock block) {
//
//    }


    private static ArrayList<CtExecutable<?>> getMethods(CtClass<?> classObject) {
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        ArrayList<CtExecutable<?>> methodsArrayList = new ArrayList<>(methodsCollection);
        ArrayList<CtExecutable<?>> constructorArrayList = getConstructors(classObject);

        methodsArrayList.addAll(constructorArrayList);
        return methodsArrayList;
    }

    private static ArrayList<CtExecutable<?>> getConstructors(CtClass<?> classObject) {
        Set<? extends CtConstructor<?>> constructorCollection = classObject.getConstructors();
        return new ArrayList<CtExecutable<?>>(constructorCollection);
    }
}
