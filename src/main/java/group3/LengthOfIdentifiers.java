package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashMap;
import java.util.List;

public class LengthOfIdentifiers extends MetricAnalysis {

    //TODO: Maybe add this code: private HashMap<CtClass<?>, String > tooShortIdentifier; //Average length of identifier doesn't say much, this will store all identifiers that have a length less than 4
    SumResult classNames, methodNames, parameterNames, variableNames;

    @Override
    public MetricReturn performAnalysis(String fileName) {
        Launcher launcher = Utilities.importCodeSample(fileName);
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));
        classNames = calculateClassNameAverage(classes);

        for (CtClass<?> c : classes) {
            calculateClassAverageLengthOfIdentifiers(c);
        }

        LengthOfIdentifiersReturn metricResult = new LengthOfIdentifiersReturn();
        metricResult.setAverageLengthOfIdentifiers(calculateCompleteAverage());
        return metricResult;
    }

    private SumResult calculateClassNameAverage(List<CtClass<?>> classes) {
        //TODO: sum length of all class names and store in classNames variable
        return classNames;
    }

    private void calculateClassAverageLengthOfIdentifiers(CtClass<?> currentClass){
        calculateSumMethods(currentClass);
        calculateSumParameters(currentClass);
        calculateSumVariables(currentClass);
    }

    private SumResult calculateSumMethods(CtClass<?> currentClass) {
        //TODO: Access all method names in class and calculate sum of letters + amount of methods, add results to methodNames variable
        return methodNames;
    }

    private SumResult calculateSumParameters (CtClass<?> currentClass) {
        //TODO: Calculate all parameter names' length in current class sum and amount
        return parameterNames;
    }

    private SumResult calculateSumVariables(CtClass<?> currentClass) {
        //TODO: Calculate  sum length of all variables in current class and store result in variableNames
        return variableNames;
    }

    private double calculateCompleteAverage() {
        double completeSum = (classNames.getSum() + methodNames.getSum() + parameterNames.getSum() + variableNames.getSum());
        double completeAmount = (classNames.getAmountOfNumbers() + methodNames.getAmountOfNumbers()
                + parameterNames.getAmountOfNumbers() + variableNames.getAmountOfNumbers());
        return completeSum/completeAmount;
    }

    static class SumResult{ //Should it be static?
        private int sum;
        private int amountOfNumbers;

        public SumResult(int sum, int amountOfNumbers) {
            this.sum = sum;
            this.amountOfNumbers = amountOfNumbers;
        }

        public int getSum() {
            return sum;
        }

        public int getAmountOfNumbers() {
            return amountOfNumbers;
        }
    }

}
