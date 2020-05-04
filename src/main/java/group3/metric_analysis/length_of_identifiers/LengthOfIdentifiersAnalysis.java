package group3.metric_analysis.length_of_identifiers;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtParameter;
import spoon.reflect.declaration.CtVariable;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author DanielSands
 * Metric analysis for average length of identifiers metric
 */
public class LengthOfIdentifiersAnalysis extends MetricAnalysis {
    private SumResult classNames, methodNames, variableNames; //Inner class that assists in calculating length averages
    private HashMap<String, Double> classLengthOfIdentifiersScores; //Stores average length of identifiers for each class
    private HashMap<String, Integer> noteworthyLengthOfIdentifierScores; //Stores class and identifier of noteworthy identifiers TODO: Change to <String, List<Integer>> so that output displays all noteworthy identifiers from same class in the same place

    /**
     * Default constructor
     * initialises private variables
     */
    public LengthOfIdentifiersAnalysis() {
        classLengthOfIdentifiersScores = new HashMap<String, Double>();
        noteworthyLengthOfIdentifierScores = new HashMap<String, Integer>();
    }

    /**
     * Returns average length of identifiers for every class
     * @return The class name and its average length of identifiers
     */
    public HashMap<String, Double> getClassLengthOfIdentifiersScores() {
        return classLengthOfIdentifiersScores;
    }

    /**
     * Returns the noteworthy identifiers, i.e. identifiers that need to be looked at again
     * @return The class and length of noteworty identifiers
     */
    public HashMap<String, Integer> getNoteworthyLengthOfIdentifierScores() {
        return noteworthyLengthOfIdentifierScores;
    }

    /**
     * Overrides MetricAnalysis abstract method. It initialises analysis process
     * @param launcher
     */
    @Override
    public void performAnalysis(Launcher launcher) {
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));
        calculateLengthOfIdentifierAverage(classes);
    }

    /**
     * This method Takes a list of classes goes through them and calculates average length of identifiers for each.
     * This averages are stored in classLengthOfIdentifiersScores.
     * @param classes
     */
    private void calculateLengthOfIdentifierAverage(List<CtClass<?>> classes) {
        for (CtClass<?> c : classes) {
            classNames = new SumResult(); //Resets all these variables for every new class
            methodNames = new SumResult();
            variableNames = new SumResult();
            int classNameLength = c.getSimpleName().length();
            if(classNameLength < 5){ //TODO: "Hard-coded" for now, might change so that user can decide what length they want to appear, maybe similar to CommentsCount?
                noteworthyLengthOfIdentifierScores.put("Class name: " + c.getSimpleName(), classNameLength); //If class name is less than 5 characters long, it will be added to noteworthy identifiers
            }
            classNames.setSum(c.getSimpleName().length());
            classNames.setAmountOfNumbers(1); //Will never be more than one class at a time here, so always one
            calculateAverageLengthOfIdentifiersWithinClass(c); //Now we've handled the class name, this method goes in to all identifiers within the class
            classLengthOfIdentifiersScores.put(c.getSimpleName(), calculateCompleteClassAverage()); //For every class, stores the complete average length of identifiers for that class
        }
    }

    /**
     * This method calculates average length of all methods, variables and parameters within a given class
     * @param currentClass
     */
    private void calculateAverageLengthOfIdentifiersWithinClass(CtClass<?> currentClass){
        calculateSumMethods(currentClass);
        calculateSumVariables(currentClass);
    }

    /**
     * This method calculates average length of all methods within a given class.
     * Also if a variable
     * @param currentClass
     */
    private void calculateSumMethods(CtClass<?> currentClass) {
        Set<CtMethod<?>> methods = currentClass.getMethods();
        for(CtMethod<?> method : methods){
            int methodLength = method.getSimpleName().length();
            if(methodLength < 5){
                noteworthyLengthOfIdentifierScores.put(method.getSignature(), methodLength); //Adds to noteworthy if less than 5 characters long
            }
            methodNames.setSum(methodNames.getSum() + methodLength); //Keeps track of the total sum of all method name lengths
            methodNames.setAmountOfNumbers(methodNames.getAmountOfNumbers() + 1); //Keeps track of the amount of methods to calculate average
        }
    }

    /**
     * This method calculates average length of all variables within a given class. This includes parameters to methods and constructors
     * @param currentClass
     */
    private void calculateSumVariables(CtClass<?> currentClass) {
        List<CtVariable<?>> variables = currentClass.getElements(new TypeFilter<CtVariable<?>>(CtVariable.class));
        for(CtVariable<?> variable : variables){
            int variableLength = variable.getSimpleName().length();
            if(variableLength < 5){
                noteworthyLengthOfIdentifierScores.put("Variable: " + variable.getSimpleName() + " in class: " +
                        currentClass.getSimpleName(), variableLength); //Adds to noteworthy if less than 5 characters long
            }
            variableNames.setSum(variableNames.getSum() + variableLength); //Keeps track of the total sum of all variable name lengths
            variableNames.setAmountOfNumbers(variableNames.getAmountOfNumbers() + 1);
        }
    }

    /**
     * Takes the sum of the length of all identifiers, divides with the amount of identifiers, to calculate average length of identifiers
     * @return Average length of identifiers for a class
     */
    private double calculateCompleteClassAverage() {
        double completeSum = (classNames.getSum() + methodNames.getSum() + variableNames.getSum());
        double completeAmount = (classNames.getAmountOfNumbers() + methodNames.getAmountOfNumbers()
                + variableNames.getAmountOfNumbers());
        return completeSum/completeAmount;
    }

    /**
     * Static nested class to keep track of different averages so that they also can be added together.
     * This allows you to calculate new averages from two or more separate ones.
     */
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
