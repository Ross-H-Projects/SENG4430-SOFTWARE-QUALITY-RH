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

    public CyclomaticAnalysis() {
        classCMCScores = new HashMap<String, Integer>();
        localresults = new HashMap<String, Integer>();
        totalCMCValue = 0;
    }

    public HashMap<String, Integer> getClassCMCScores() {
        return classCMCScores;
    }

    public void performAnalysis (Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            for (CtMethod<?> methodObject : Query.getElements(classObject, new TypeFilter<CtMethod<?>>(CtMethod.class))) {
                int ifCount = 0, elseCount = 0, conditionCount = 0, forCount = 0, whileCount = 0, caseCount = 0;
                ifCount = countIfs(methodObject);
                elseCount = countElses(methodObject);
                conditionCount = countConditions(methodObject);
                forCount = countFors(methodObject);
                whileCount = countWhiles(methodObject);
                caseCount = countCases(methodObject);
                localresults.put("IF", ifCount);
                localresults.put("ELSE", elseCount);
                localresults.put("CONDITIONS", conditionCount);
                localresults.put("FOR", forCount);
                localresults.put("WHILE", whileCount);
                localresults.put("CASE", caseCount);
                int total = ifCount + elseCount + conditionCount + forCount + whileCount + caseCount;
                total += 1;
                totalCMCValue += total;
                classCMCScores.put(classObject.getQualifiedName()+"."+methodObject.getSimpleName() + ": CMC", total);
            }
        }
    }

    private int countIfs (CtMethod<?> ifin){
        List<CtIf> ifCalls = ifin.getElements(new TypeFilter<CtIf>(CtIf.class));
        return ifCalls.size();
    }

    private int countFors (CtMethod<?> Forin){
        List<CtFor> forCalls = Forin.getElements(new TypeFilter<CtFor>(CtFor.class));
        List<CtForEach> forEachCalls = Forin.getElements(new TypeFilter<CtForEach>(CtForEach.class));
        return forCalls.size()+forEachCalls.size();
    }

    private int countWhiles (CtMethod<?> Whilein){
        List<CtWhile> whileCalls = Whilein.getElements(new TypeFilter<CtWhile>(CtWhile.class));
        return whileCalls.size();
    }

    private int countCases (CtMethod<?> Casein){
        List<CtCase> caseCalls = Casein.getElements(new TypeFilter<CtCase>(CtCase.class));
        return caseCalls.size();
    }

    private int countElses (CtMethod<?> ifin){
        int elseCount = 0;
        List<CtIf> ifCalls = ifin.getElements(new TypeFilter<CtIf>(CtIf.class));
        for (CtIf ifObject : ifCalls){
            if (ifObject.getElseStatement() != null) {
                elseCount++;
            }
        }
        return elseCount;
    }

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
        return conditionCount;
    }
}
