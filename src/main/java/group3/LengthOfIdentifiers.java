package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;
import java.util.Set;

public class LengthOfIdentifiers extends MetricAnalysis {

    //TODO: Maybe add this code: private HashMap<CtClass<?>, String > tooShortIdentifier; //Average length of identifier doesn't say much, this will store all identifiers that have a length less than 4
    SumResult classNames, methodNames, parameterNames, variableNames;

    @Override
    public MetricReturn performAnalysis(String fileName) {
        Launcher launcher = Utilities.importCodeSample(fileName);
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));
        calculateLengthOfIdentifierAverage(classes); //This is where the magic starts
        LengthOfIdentifiersReturn metricResult = new LengthOfIdentifiersReturn();
        metricResult.setAverageLengthOfIdentifiers(calculateCompleteAverage());
        return metricResult;
    }

    private void calculateLengthOfIdentifierAverage(List<CtClass<?>> classes) {
        classNames = new SumResult();
        methodNames = new SumResult();
        parameterNames = new SumResult();
        variableNames = new SumResult();
        for (CtClass<?> c : classes) {
            classNames.setSum(classNames.getSum()+ c.getSimpleName().length()); //TODO: Is c.getSimpleName() the correct method here? getLabel()? getQualifiedName()? toString()? prettyprint()?
            classNames.setAmountOfNumbers(classNames.getAmountOfNumbers() + 1);
            calculateAverageLengthOfIdentifiersWithinClass(c);
        }
    }

    private void calculateAverageLengthOfIdentifiersWithinClass(CtClass<?> currentClass){
        calculateSumMethodsAndParameters(currentClass);
        calculateSumVariables(currentClass);
    }

    private void calculateSumMethodsAndParameters(CtClass<?> currentClass) {
        Set<CtMethod<?>> methods = currentClass.getMethods();
        for(CtMethod<?> method : methods){
            methodNames.setSum(methodNames.getSum() + method.getSimpleName().length()); //TODO: Is getSimpleName() correct? toString()?
            methodNames.setAmountOfNumbers(methodNames.getAmountOfNumbers() + 1);
            calculateSumParameters(method.getParameters());
        }
    }

    private void calculateSumParameters (List<CtParameter<?>> parameters) {
        for (CtParameter<?> parameter : parameters){
            parameterNames.setSum(parameterNames.getSum() + parameter.getSimpleName().length());//TODO: Is getSimpleName() correct?
            parameterNames.setAmountOfNumbers(parameterNames.getAmountOfNumbers() + 1);
        }
    }

    private void calculateSumVariables(CtClass<?> currentClass) {
        //TODO: Calculate  sum length of all variables in current class and store result in variableNames
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
