package group3.metric_analysis.length_of_identifiers;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class LengthOfIdentifiersAnalysis extends MetricAnalysis {
    private SumResult classNames, methodNames, parameterNames, variableNames;
    private HashMap<String, Double> classLengthOfIdentifiersScores; //TODO: Change to <String, List of Doubles>
    private HashMap<String, Integer> noteworthyLengthOfIdentifierScores; //TODO: Change to <String, List of Integers>

    public LengthOfIdentifiersAnalysis() {
        classLengthOfIdentifiersScores = new HashMap<String, Double>();
        noteworthyLengthOfIdentifierScores = new HashMap<String, Integer>();
    }

    public HashMap<String, Double> getClassLengthOfIdentifiersScores() {
        return classLengthOfIdentifiersScores;
    }
    public HashMap<String, Integer> getNoteworthyLengthOfIdentifierScores() {
        return noteworthyLengthOfIdentifierScores;
    }

    @Override
    public void performAnalysis(Launcher launcher) {
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));
        calculateLengthOfIdentifierAverage(classes);
    }
    private void calculateLengthOfIdentifierAverage(List<CtClass<?>> classes) {
        for (CtClass<?> c : classes) {
            classNames = new SumResult(); //Resets all these variables for every new class
            methodNames = new SumResult();
            parameterNames = new SumResult();
            variableNames = new SumResult();
            classNames.setSum(c.getSimpleName().length());
            classNames.setAmountOfNumbers(1);
            calculateAverageLengthOfIdentifiersWithinClass(c);
            classLengthOfIdentifiersScores.put(c.getSimpleName(), calculateCompleteClassAverage());
        }
    }

    private void calculateAverageLengthOfIdentifiersWithinClass(CtClass<?> currentClass){
        calculateSumMethodsAndParameters(currentClass);
        calculateSumVariables(currentClass);
    }

    private void calculateSumMethodsAndParameters(CtClass<?> currentClass) {
        Set<CtMethod<?>> methods = currentClass.getMethods();
        for(CtMethod<?> method : methods){
            int methodLength = method.getSimpleName().length();
            if(methodLength < 5){ //TODO: "Hard-coded" for now, might change so that user can decide what length they want to appear, maybe similar to CommentsCount?
                noteworthyLengthOfIdentifierScores.put(method.getSignature(), methodLength);
            }
            methodNames.setSum(methodNames.getSum() + methodLength);
            methodNames.setAmountOfNumbers(methodNames.getAmountOfNumbers() + 1);
            calculateSumParameters(method, currentClass);
        }
    }

    private void calculateSumParameters (CtMethod<?> method, CtClass<?> currentClass) {
        List<CtParameter<?>> parameters = method.getParameters();
        for (CtParameter<?> parameter : parameters){
            int parameterLength = parameter.getSimpleName().length();
            if(parameterLength < 5){
                noteworthyLengthOfIdentifierScores.put("Parameter " + parameter.getSimpleName() +
                        " for method: " + method.getSignature() + "in class: " + currentClass.getSimpleName(), parameterLength);
            }
            parameterNames.setSum(parameterNames.getSum() + parameterLength);
            parameterNames.setAmountOfNumbers(parameterNames.getAmountOfNumbers() + 1);
        }
    }

    private void calculateSumVariables(CtClass<?> currentClass) {
        List<CtVariable<?>> variables = currentClass.getElements(new TypeFilter<CtVariable<?>>(CtVariable.class));
        for(CtVariable<?> variable : variables){
            int variableLength = variable.getSimpleName().length();
            if(variableLength < 5){
                noteworthyLengthOfIdentifierScores.put("Variable: " + variable.getSimpleName() + " in class: " + currentClass.getSimpleName(), variableLength);
            }
            variableNames.setSum(variableNames.getSum() + variableLength);
            variableNames.setAmountOfNumbers(variableNames.getAmountOfNumbers() + 1);
        }
    }

    private double calculateCompleteClassAverage() {
        double completeSum = (classNames.getSum() + methodNames.getSum() + parameterNames.getSum() + variableNames.getSum());
        double completeAmount = (classNames.getAmountOfNumbers() + methodNames.getAmountOfNumbers()
                + parameterNames.getAmountOfNumbers() + variableNames.getAmountOfNumbers());
        return completeSum/completeAmount;
    }

    static class SumResult{ //Should it be static?
        private int sum;
        private int amountOfNumbers;

        public SumResult() {
            this.sum = 0;
            this.amountOfNumbers = 0;
        }

        public int getSum() {
            return sum;
        }

        public int getAmountOfNumbers() {
            return amountOfNumbers;
        }

        public void setSum(int sum) {
            this.sum = sum;
        }

        public void setAmountOfNumbers(int amountOfNumbers) {
            this.amountOfNumbers = amountOfNumbers;
        }
    }
}
