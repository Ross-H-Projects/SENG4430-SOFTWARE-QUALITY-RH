package group3.metric_analysis.conditional_nesting;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtIf;
import spoon.reflect.declaration.*;
import spoon.reflect.path.CtRole;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;


import java.util.*;


public class ConditionalNestingAnalysis extends MetricAnalysis {
    private int conditionalNestingLimit;
    private HashMap<String, HashMap<String, Integer>> classConditionalNestingScores;

    public ConditionalNestingAnalysis() {
        conditionalNestingLimit = 3;
        classConditionalNestingScores = new HashMap<String, HashMap<String, Integer>>();
    }
    public String getClassConditionalNestingScoresJson() {
        HashMap<String, HashMap<String, Integer>> finalClassConditionalNestingScores = getClassConditionalNestingScores();
        finalClassConditionalNestingScores.toString();
        return finalClassConditionalNestingScores.toString();
    }
    public HashMap<String, HashMap<String, Integer>> getClassConditionalNestingScores() {
        HashMap<String, HashMap<String, Integer>> finalClassConditionalNestingScores = new HashMap<String, HashMap<String, Integer>>();

        for (Map.Entry<String, HashMap<String, Integer>> classObject : classConditionalNestingScores.entrySet()) {
            String classString = classObject.getKey();
            HashMap<String, Integer> MethodMaxDepth = classObject.getValue();

            HashMap<String, Integer> finalMethodConditionalNestingScores = new HashMap<String, Integer>();
            for (Map.Entry<String, Integer> methodObject : MethodMaxDepth.entrySet()) {
                String methodString = methodObject.getKey();
                int maxDepth = methodObject.getValue();
                if(maxDepth >= conditionalNestingLimit) {
                    finalMethodConditionalNestingScores.put(methodString, maxDepth);
                }
            }
            if(finalMethodConditionalNestingScores.size() > 0) {
                finalClassConditionalNestingScores.put(classString, finalMethodConditionalNestingScores);
            }
        }
        return finalClassConditionalNestingScores;
    }

    public void performAnalysis (Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, Integer> methodConditionalNestingScores = new HashMap<String, Integer>();
            for (CtExecutable<?> methodObject : getMethods(classObject)) {
                int maxDepth = doDepth(methodObject);
                methodConditionalNestingScores.put(methodObject.getSignature(), maxDepth);
            }
            classConditionalNestingScores.put(classObject.getQualifiedName(), methodConditionalNestingScores);
        }
    }
    public static boolean isElseInIfStatement(CtIf ifStatement) {
        Iterator<CtElement> i = ifStatement.getElseStatement().descendantIterator();
        i.next();
        CtElement firstElementInElseBlock = i.next();
        try {
            //is else if in current if statement
            ifStatement = (CtIf) firstElementInElseBlock;
            return false;
        } catch (Exception e) {
            //is else in current if statement
            return true;
        }
    }
    public static int doDepth(CtExecutable<?> methodObject) {
        if (methodObject.getDirectChildren().size() <= 1) {
            return 0;
        } else {
            return doDepthOnCodeBlock(-1, methodObject.getDirectChildren().get(1));
        }
    }

    public static int doDepthOnCodeBlock(int depth, CtElement codeBlock) {
        depth+=1;
        ArrayList<Integer> depthList = new ArrayList<Integer>();
        CtIf ifStatement = getFirstIfStatementFromCodeBlock(codeBlock);
        while(ifStatement != null) {
            int currentDepth = doDepthOnCodeBlock(depth, ifStatement.getThenStatement());
            depthList.add(currentDepth);
            if(ifStatement.getElseStatement() != null) {
                boolean isElse = isElseInIfStatement(ifStatement);
                if(isElse) {
                    //if next statement is else.
                    currentDepth = doDepthOnCodeBlock(depth, ifStatement.getElseStatement());
                    depthList.add(currentDepth);
                    ifStatement = null;
                } else {
                    ifStatement = getFirstIfStatementFromCodeBlock(ifStatement.getElseStatement());
                }
            } else {
                break;
            }
        }
        if (depthList.size() == 0) {
            return depth;
        } else {
            return Collections.max(depthList);
        }
    }
    public static CtIf getFirstIfStatementFromCodeBlock(CtElement codeBlock) {
        ArrayList<CtIf> ifStatements = (ArrayList<CtIf>) Query.getElements(codeBlock, new TypeFilter<CtIf>(CtIf.class));
        if (ifStatements.size() > 0) {
            return ifStatements.get(0);
        } else {
            return null;
        }
    }
    public static ArrayList<CtExecutable<?>> getMethodsFromClassObject(CtClass<?> classObject) {
        return getMethods(classObject);
    }
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
