package group3.metric_analysis.cyclomatic_complexity;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.code.CtExpression;
import spoon.reflect.code.CtIf;
import spoon.reflect.code.*;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;


import java.util.*;


public class CyclomaticAnalysis extends MetricAnalysis {

    private HashMap<String, Integer> classCMCScores;
    private HashMap<String, Integer> localresults;
    private Integer totalCMCValue;

    //Constructor
    public CyclomaticAnalysis() {
        classCMCScores = new HashMap<String, Integer>();
        localresults = new HashMap<String, Integer>();
        totalCMCValue = 0;
    }

    //Returns hashmap of complexity values
    public HashMap<String, Integer> getClassCMCScores() {
        return classCMCScores;
    }

    //Analyzes the given files
    public void performAnalysis (Launcher launcher) {
        //Iterates through each class and method the program was supplied with
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            for (CtMethod<?> methodObject : Query.getElements(classObject, new TypeFilter<CtMethod<?>>(CtMethod.class))) {
                //Counts the number of various types of decision points that calculate cyclomatic complexity
                int ifCount = 0, conditionCount = 0, forCount = 0, whileCount = 0, caseCount = 0;
                ifCount = countIfs(methodObject);
                conditionCount = countConditions(methodObject);
                forCount = countFors(methodObject);
                whileCount = countWhiles(methodObject);
                caseCount = countCases(methodObject);
                localresults.put("IF", ifCount);
                localresults.put("CONDITIONS", conditionCount);
                localresults.put("FOR", forCount);
                localresults.put("WHILE", whileCount);
                localresults.put("CASE", caseCount);
                //Totals the values per method and adds them to the hashmap
                int total = ifCount + conditionCount + forCount + whileCount + caseCount;
                total += 1;
                totalCMCValue += total;
                classCMCScores.put(classObject.getQualifiedName()+"."+methodObject.getSimpleName(), total);
            }
        }
    }

    //Counts the ifs in the method
    private int countIfs (CtMethod<?> ifin){
        List<CtIf> ifCalls = ifin.getElements(new TypeFilter<CtIf>(CtIf.class));
        return ifCalls.size();
    }

    //Counts the fors in the method
    private int countFors (CtMethod<?> Forin){
        List<CtFor> forCalls = Forin.getElements(new TypeFilter<CtFor>(CtFor.class));
        List<CtForEach> forEachCalls = Forin.getElements(new TypeFilter<CtForEach>(CtForEach.class));
        return forCalls.size()+forEachCalls.size();
    }

    //Counts the whiles in the method
    private int countWhiles (CtMethod<?> Whilein){
        List<CtWhile> whileCalls = Whilein.getElements(new TypeFilter<CtWhile>(CtWhile.class));
        return whileCalls.size();
    }

    //Counts the cases in the method, removing any "default" cases from the count
    private int countCases (CtMethod<?> Casein){
        List<CtCase> caseCalls = Casein.getElements(new TypeFilter<CtCase>(CtCase.class));
        int output = caseCalls.size();
        for (CtCase caseint : caseCalls){
            if (caseint.toString().toLowerCase().replace(" ","").contains("default:")){
                output -= 1;
            }
        }
        return output;
    }

    //Counts the conditions, such as && or ||, placed within if/while statements
    private int countConditions (CtMethod<?> ifin){
        int conditionCount = 0;
        List<String> foundConditions = new ArrayList<>();
        List<CtIf> ifCalls = ifin.getElements(new TypeFilter<CtIf>(CtIf.class));
        for (CtIf ifObject : ifCalls){
            if (ifObject.getCondition() != null){
                if (ifObject.getCondition().toString().contains("&&") || ifObject.getCondition().toString().contains("||")){
                    if (!foundConditions.contains(ifObject.getCondition().toString())) {
                        foundConditions.add(ifObject.getCondition().toString());
                        conditionCount++;
                    }
                }
            }
        }
        List<CtWhile> whileCalls = ifin.getElements(new TypeFilter<CtWhile>(CtWhile.class));
        for (CtWhile whileObject : whileCalls){
            if (whileObject.getLoopingExpression() != null){
                if (whileObject.getLoopingExpression().toString().contains("&&") || whileObject.getLoopingExpression().toString().contains("||")){
                    if (!foundConditions.contains(whileObject.getLoopingExpression().toString())) {
                        foundConditions.add(whileObject.getLoopingExpression().toString());
                        conditionCount++;
                    }
                }
            }
        }
        return conditionCount;
    }
}
