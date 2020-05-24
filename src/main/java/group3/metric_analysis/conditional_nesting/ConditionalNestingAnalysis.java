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
                break;
            }
            classConditionalNestingScores.put(classObject.getQualifiedName(), methodConditionalNestingScores);
        }
    }

    public static boolean determineIfElementIsIf(CtElement element) {
        for (CtIf ifStatement : Query.getElements(element, new TypeFilter<CtIf>(CtIf.class))) {
            if (ifStatement==element) {
                return true;
            }
        }
        return false;
    }

    public static boolean determineIfElementIsElse(CtElement elseCodeBlock) {
        if(elseCodeBlock.getRoleInParent() == CtRole.ELSE) {
            CtElement parent = elseCodeBlock.getParent();
            for (CtIf ifStatement : Query.getElements(parent, new TypeFilter<CtIf>(CtIf.class))) {
                System.out.println("!"+ifStatement);
//                System.out.println(ifStatement.getElseStatement().toString());
            }
        }
        return true;
    }
    public static boolean getTopOfIfStatement(CtIf ifStatement, CtBlock elseBlock) {
        if(elseBlock.getRoleInParent() == CtRole.ELSE) {
            CtIf currentIfStatement= ifStatement;
            CtBlock currentCodeBlock= ifStatement.getElseStatement();
            while(currentCodeBlock != null && currentCodeBlock != elseBlock) {
                currentCodeBlock = ifStatement.getElseStatement();
                currentIfStatement = Query.getElements(currentCodeBlock, new TypeFilter<CtIf>(CtIf.class)).get(0);
                currentCodeBlock = currentIfStatement.getElseStatement();
            }
            if(currentCodeBlock == null) {
                return false;
            }
            if (currentCodeBlock == elseBlock) {
                return true;
            }
        }
        return false;
    }
    public static int getIfStatementDepthForMethod(CtExecutable<?> methodObject) {
        int maxDepth = 0;
        Collection<CtIf> ifStatementsCollection = Query.getElements(methodObject, new TypeFilter<CtIf>(CtIf.class));
        ArrayList<CtIf> ifStatements = new ArrayList<CtIf>(ifStatementsCollection);
        CtElement element = ifStatements.get(12);
//        System.out.println(element + " " + element.getRoleInParent());
//        element = element.getParent();
//        System.out.println(element + " " + element.getRoleInParent());
//        element = element.getParent();
//        System.out.println(element + " " + element.getRoleInParent());
//        element = element.getParent();

        //        System.out.println(element + " " + element.getRoleInParent());
//        element = element.getParent();
        System.out.println("------");
        element = ifStatements.get(12).getElseStatement();
        int depthCount=0;
        boolean atBody = false;
        while(! atBody) {
            if(element.getRoleInParent() == CtRole.BODY) {
                atBody = true;
            }
//            System.out.println(element.getRoleInParent() == CtRole.BODY);
//            System.out.println(element + " " + element.getRoleInParent());
//            if(element.getRoleInParent() == CtRole.STATEMENT) {
//                System.out.println("IF STATEMENT");
//                element = element.getParent();
//                depthCount++;
//            }
            if(element.getRoleInParent() == CtRole.ELSE) {
                boolean isSameIfStatement=false;
//                System.out.println("ELSE STATEMENT");
                do {
                    CtIf ifStatement = (CtIf)element.getParent();
                    CtBlock codeBlock = (CtBlock)element;
                    isSameIfStatement=getTopOfIfStatement(ifStatement, codeBlock);
//                    System.out.println(element);
                    element = element.getParent().getParent();
                } while(isSameIfStatement);
                depthCount++;
            } else {
//                System.out.println("THEN STATEMENT");
//                System.out.println(element);
                element = element.getParent().getParent();
                depthCount++;
            }
        }
        System.out.println(depthCount);
//        System.out.println(element + " " + element.getRoleInParent());

//        element = element.getParent();
//        System.out.println(element + " " + element.getRoleInParent());
//
//        element = element.getParent();
//        System.out.println(element + " " + element.getRoleInParent());
//
//        element = element.getParent();
//        System.out.println(element + " " + element.getRoleInParent());

//        element = element.getParent();
//        boolean a = getTopOfIfStatement((CtIf)element, ((CtIf) element).getElseStatement());
//        System.out.println(a);
//        element = element.getParent();
//        System.out.println(element + " " + element.getRoleInParent());
//        a = getTopOfIfStatement((CtIf)element, ((CtIf) element).getElseStatement());
//        System.out.println(a);

//        if else. get parent. navigate down all else statements and check if else is part of same statement. if true get parent
//        if else. get parent. navigate down all else statements and check if else is part of same statement. if true get parent
////        for (CtIf ifStatement : Query.getElements(methodObject, new TypeFilter<CtIf>(CtIf.class))) {
////        for (CtIf ifStatement : ifStatements) {
//        CtIf ifStatement = ifStatements.get(11);
//        int currentDepth = 1;
//        CtElement element = ifStatement;
//        while (element.getRoleInParent() != CtRole.BODY) {
//            System.out.println(element + " " + element.getRoleInParent());
////            System.out.println("BUT IS IT ACTUALLY ELSE CHECK BETWEEN 3 and 4")
//            if(element.getRoleInParent() == CtRole.THEN || element.getRoleInParent() == CtRole.ELSE) {
//                if (element.getParent().getRoleInParent() == CtRole.STATEMENT) {
//                    currentDepth++;
////                    System.out.println(element.getParent().toString());
//                    System.out.println("!!!" + currentDepth);
//                    element = element.getParent().getParent();
//                }
//            } else {
//                element = element.getParent();
//            }
//        }
//        if (currentDepth > maxDepth) {
//            maxDepth = currentDepth;
//        }
//        System.out.println(maxDepth);
        return maxDepth;
    }

    public static int getIfStatementDepthForMethodold(CtExecutable<?> methodObject) {
        int maxDepth = 0;
        for (CtIf ifStatement : Query.getElements(methodObject, new TypeFilter<CtIf>(CtIf.class))) {
            if(ifStatement.getParent().getRoleInParent() == CtRole.BODY) {
                CtBlock codeBlock = ifStatement.getThenStatement();
                int currentDepth = getIfs(codeBlock, 1);
                if (currentDepth > maxDepth){
                    maxDepth = currentDepth;
                }
//                CtBlock elseCodeBlock = getElseStatement(ifStatement);
//                int maxDepthElse = getIfs(elseCodeBlock, 1);
//                if (Math.max(currentDepth,  maxDepthElse) > maxDepth) {
//                    maxDepth = Math.max(currentDepth, maxDepthElse);
//                }
            }
        }
        System.out.println(maxDepth);
        return maxDepth;
    }

    public static CtElement getParentThenBlock(CtElement element) {
        while(element.getParent().getRoleInParent() != CtRole.THEN) {
            element = element.getParent();
        }
        return element.getParent();
    }

    public static int getIfs(CtBlock currentCodeBlock, int maxDepth) {
        int currentDepth = maxDepth+1;
        ArrayList<Integer> depthArray = new ArrayList<Integer>();
        if (currentCodeBlock != null) {
            for (CtIf ifStatement : Query.getElements(currentCodeBlock, new TypeFilter<CtIf>(CtIf.class))) {
                CtElement parentThenBlock = getParentThenBlock(ifStatement);
                if(parentThenBlock == currentCodeBlock) {
//                    System.out.println(ifStatement);
                    CtBlock codeBlock = ifStatement.getThenStatement();
                    currentDepth = getIfs(codeBlock, currentDepth);
//                    System.out.println(currentDepth + " " + maxDepth);
                    depthArray.add(currentDepth);
                    System.out.println("ADDING TO ARRAY"+depthArray);
                    if (currentDepth > maxDepth) {
                        maxDepth = currentDepth;
                    }
                }
//                System.out.println(ifStatement.getRoleInParent());
//                System.out.println(ifStatement.getParent());
//                System.out.println(ifStatement.getParent().getRoleInParent());
////                System.out.println(ifStatement.getParent().getParent().getParent().getRoleInParent());
//                System.out.println("000000000000000000000000000000000000");
            }
                //            for (CtIf ifStatement : Query.getElements(currentCodeBlock, new TypeFilter<CtIf>(CtIf.class))) {
//                System.out.println(ifStatement +" "+ifStatement.getParent());
//                if(currentCodeBlock == ifStatement.getParent()) {
//                    CtBlock codeBlock = ifStatement.getThenStatement();
//                    currentDepth = getIfs(codeBlock, currentDepth);
//                    if (currentDepth > maxDepth) {
//                        maxDepth = currentDepth;
//                    }
//                }
//            }
//        System.out.println("Blocks max depth "+ maxDepth);
//        System.out.println("---------------------");
//            Collection<CtIf> ifStatementsCollection = Query.getElements(currentCodeBlock, new TypeFilter<CtIf>(CtIf.class));
//            ArrayList<CtIf> ifStatements = new ArrayList<CtIf>(ifStatementsCollection);
//            if (ifStatements.size() > 0) {
//                CtBlock elseStatementBlock = getElseStatement(ifStatements.get(0));
//                currentDepth = getIfs(elseStatementBlock, currentDepth);
//                if (currentDepth > maxDepth) {
//                    maxDepth = currentDepth;
//                }
//            }
        }
        if(depthArray.size() <= 0) {
            return 0;
        } else {
            System.out.println(Collections.max(depthArray));
            return Collections.max(depthArray);
        }
//        return maxDepth;
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
