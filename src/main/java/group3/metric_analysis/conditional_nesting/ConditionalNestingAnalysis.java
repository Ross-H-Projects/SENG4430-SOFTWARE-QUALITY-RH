package group3.metric_analysis.conditional_nesting;

import group3.MetricAnalysis;
import spoon.Launcher;
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
        //Default conditional nesting depth limit. If >= then method has too much if statement depth
        conditionalNestingLimit = 3;
        //HashMap<Class Name, HashMap<Method Name, If Statement Depth>>
        classConditionalNestingScores = new HashMap<String, HashMap<String, Integer>>();
    }
    //Returns json output of calculated class's conditional nesting scores >= nesting limit
    public String getClassConditionalNestingScoresJson() {
        HashMap<String, HashMap<String, Integer>> finalClassConditionalNestingScores = getClassConditionalNestingScores();
        return finalClassConditionalNestingScores.toString();
    }
    //Uses the calculated class's conditional nesting scores and compares them to the conditionalNestingLimit.
    //Returns a hashmap of the class's methods that are >= nesting limit
    public HashMap<String, HashMap<String, Integer>> getClassConditionalNestingScores() {
        HashMap<String, HashMap<String, Integer>> finalClassConditionalNestingScores = new HashMap<String, HashMap<String, Integer>>();

        for (Map.Entry<String, HashMap<String, Integer>> classObject : classConditionalNestingScores.entrySet()) {
            String classString = classObject.getKey();
            HashMap<String, Integer> MethodMaxDepth = classObject.getValue();

            HashMap<String, Integer> finalMethodConditionalNestingScores = new HashMap<String, Integer>();
            for (Map.Entry<String, Integer> methodObject : MethodMaxDepth.entrySet()) {
                String methodString = methodObject.getKey();
                int maxDepth = methodObject.getValue();
                //Compares max depth of method against conditionalNestingLimit. If >= method is placed inside hashmap
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
    //Main analysis method
    //Retrieves all classes from the Spoon launcher object. Loops over each class's method and calculates their conditional nesting score
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
    //Checks if Else Statement is the next code block following an if statement
    //Important for differentiating an else if and an else
    public boolean isElseInIfStatement(CtIf ifStatement) {
        //From the else statement of an if statement, retrieve the first element in the block
        Iterator<CtElement> i = ifStatement.getElseStatement().descendantIterator();
        i.next();
        CtElement firstElementInElseBlock = i.next();
        try {
            //is else if block current element. The type cast from CtElement succeeds and returns false
            ifStatement = (CtIf) firstElementInElseBlock;
            return false;
        } catch (Exception e) {
            //if the above try fails, the retrieved block is a else block
            //is else block current element returns true
            return true;
        }
    }
    //Calculates depth on method object
    public int doDepth(CtExecutable<?> methodObject) {
        int maxDepth=0;
        //Check if method body is empty
        if (methodObject.getDirectChildren().size() <= 1) {
            return maxDepth;
        } else {
            //iterate through each if statement in method body
            for (CtIf ifStatements : Query.getElements(methodObject, new TypeFilter<CtIf>(CtIf.class))) {
                //check if if statements parent is method body. If so it is the top of the tree of if statements
                if(ifStatements.getParent().getRoleInParent() == CtRole.BODY) {
                    //calculate current depth of if statement blocks
                    int currentDepth = doDepthOnCodeBlock(-1, (CtElement) ifStatements);
                    if(currentDepth > maxDepth) {
                        maxDepth = currentDepth;
                    }
                }
            }
        }
        return maxDepth;
    }
    //Calculates depth on CtElement code block
    public int doDepthOnCodeBlock(int depth, CtElement codeBlock) {
        depth+=1;
        ArrayList<Integer> depthList = new ArrayList<Integer>();
        CtIf ifStatement = getFirstIfStatementFromCodeBlock(codeBlock);
        //while current if statement is not null
        while(ifStatement != null) {
            //calculate depth of then statement
            int currentDepth = doDepthOnCodeBlock(depth, ifStatement.getThenStatement());
            //add depth to depth array list
            depthList.add(currentDepth);
            //If if statement has a following else if or else statement
            if(ifStatement.getElseStatement() != null) {
                //check if statement is else if or else
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
        //if depth list is empty return current depth
        if (depthList.size() == 0) {
            return depth;
        } else {
            //get max depth from depthList
            return Collections.max(depthList);
        }
    }
    public CtIf getFirstIfStatementFromCodeBlock(CtElement codeBlock) {
        ArrayList<CtIf> ifStatements = (ArrayList<CtIf>) Query.getElements(codeBlock, new TypeFilter<CtIf>(CtIf.class));
        if (ifStatements.size() > 0) {
            return ifStatements.get(0);
        } else {
            return null;
        }
    }
    //public class to enable testing in junit on getMethods()
    public ArrayList<CtExecutable<?>> getMethodsFromClassObject(CtClass<?> classObject) {
        return getMethods(classObject);
    }
    private ArrayList<CtExecutable<?>> getMethods(CtClass<?> classObject) {
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        ArrayList<CtExecutable<?>> methodsArrayList = new ArrayList<>(methodsCollection);
        ArrayList<CtExecutable<?>> constructorArrayList = getConstructors(classObject);
        //The constructor and method array list is joined together to return a complete method list of a class
        methodsArrayList.addAll(constructorArrayList);
        return methodsArrayList;
    }
    private ArrayList<CtExecutable<?>> getConstructors(CtClass<?> classObject) {
        Set<? extends CtConstructor<?>> constructorCollection = classObject.getConstructors();
        //The constructor is returned as a set collection. So it is type casted as an ArrayList to maintain consistency of returned type
        return new ArrayList<CtExecutable<?>>(constructorCollection);
    }
}
