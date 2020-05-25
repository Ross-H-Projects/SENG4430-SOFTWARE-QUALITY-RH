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
                    ifStatement = null;
                } else {
                    currentDepth = doDepthOnCodeBlock(depth, ifStatement.getElseStatement());
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

    public static boolean determineIfElementIsIf(CtElement element) {
        for (CtIf ifStatement : Query.getElements(element, new TypeFilter<CtIf>(CtIf.class))) {
            if (ifStatement==element) {
                return true;
            }
        }
        return false;
    }
    public static CtElement getTopOfIfStatementFromElseBlock(CtElement element) {
        System.out.println("THIS IS ELEMENT" + element);
        CtIf ifStatement = (CtIf) element.getParent();
        CtBlock currentBlock = (CtBlock) ifStatement.getParent();
        CtIf checkedIfStatement = ifStatement;
        boolean atTop = false;
        while(!atTop) {
            if (currentBlock.getRoleInParent() == CtRole.BODY) {
                atTop = true;
            } else if (currentBlock.getRoleInParent() == CtRole.THEN) {
                atTop = true;
            } else if (currentBlock.getRoleInParent() == CtRole.ELSE) {
                while (checkedIfStatement.getElseStatement() != null) {
                    if (checkedIfStatement.getElseStatement() == element) {
                        break;
                    }
                    CtElement checkedBlock = checkedIfStatement.getElseStatement();
                    checkedIfStatement = Query.getElements(checkedBlock, new TypeFilter<CtIf>(CtIf.class)).get(0);
                }

                if (checkedIfStatement.getElseStatement() == null) {
                    atTop = true;
                    ifStatement = (CtIf)ifStatement.getParent().getParent();
                    currentBlock = (CtBlock) ifStatement.getParent();
                } else if (checkedIfStatement.getElseStatement() == element) {
                    ifStatement = checkedIfStatement;
                    System.out.println("FIRST WHILE\n" + checkedIfStatement);
                    checkedIfStatement = (CtIf) checkedIfStatement.getParent().getParent();
                    currentBlock = (CtBlock) checkedIfStatement.getParent();
                    System.out.println("SECOND WHILE\n" + currentBlock);
                }
            }
        }
        System.out.println(currentBlock.getRoleInParent() + " " +ifStatement.getParent());
        return ifStatement.getParent();
    }
    public static int getIfStatementDepthForMethod(CtExecutable<?> methodObject) {
        int maxDepth = 0;
        ArrayList<Integer> depthList = new ArrayList<Integer>();
//        CtElement element =  Query.getElements(methodObject, new TypeFilter<CtIf>(CtIf.class)).get(5);
//        int depthCount = getDepthOfIf(element);
//        depthList.add(depthCount);
        for (CtElement element : Query.getElements(methodObject, new TypeFilter<CtIf>(CtIf.class))) {
            int depthCount = getDepthOfIf(element);
            depthList.add(depthCount);
        }
        if (depthList.size() == 0) {
            return 0;
        } else {
            return Collections.max(depthList);
        }
    }
    public static int getDepthOfIf(CtElement element) {
        int depthCount = 0;
        boolean atBody = false;
        while (!atBody) {
//            System.out.println(element + " " + depthCount);
            if (element.getRoleInParent() == CtRole.BODY) {
//                System.out.println("IS BODY ");
                atBody = true;
            }
            else if (element.getRoleInParent() == CtRole.STATEMENT) {
//                System.out.println("IS STATEMENT ");
                element = element.getParent();
                depthCount++;
            }
            else if (element.getRoleInParent() == CtRole.ELSE) {
                element = getTopOfIfStatementFromElseBlock(element);
                if(element.getRoleInParent() == CtRole.BODY) {
                    break;
                }
                depthCount++;
            } else {
//                System.out.println("IS THEN");
                element = element.getParent().getParent();
                depthCount++;
            }
//            System.out.println(depthCount);
        }
        return depthCount;
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
