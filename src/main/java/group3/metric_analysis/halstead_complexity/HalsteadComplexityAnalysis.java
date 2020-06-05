package group3.metric_analysis.halstead_complexity;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.*;
import spoon.reflect.code.*;
import spoon.reflect.visitor.*;
import spoon.support.reflect.code.*;

public class HalsteadComplexityAnalysis extends MetricAnalysis{
    private int n1, n2, N1, N2;

    private ArrayList<Integer>halsteadNumbers;
    private HashMap<String, String> complexityMeasures;
    private HashMap<String, CtClass> ctClasses;

    private HashMap<String, Integer> distinctOperators;
    private HashMap<String, Integer> distinctOperands;

    private HashMap<String, Integer> distinctAssgnOperators;
    private HashMap<String, Integer> distinctAssgnOperands;


    public HalsteadComplexityAnalysis() {
        n1 = 0;                             //n1 = the number of distinct operators
        n2 = 0;                             //n2 = the number of distinct operands
        N1 = 0;                             //N1 = the total number of operators
        N2 = 0;                             //N2 = the total number of operands

        ctClasses = new HashMap<String, CtClass>();

        halsteadNumbers = new ArrayList<Integer>();

        complexityMeasures = new HashMap<String, String>();

        distinctOperators = new HashMap<String, Integer>();
        distinctOperands = new HashMap<String, Integer>();

        distinctAssgnOperators = new HashMap<String, Integer>();
        distinctAssgnOperands = new HashMap<String, Integer>();

    }

    public void performAnalysis (Launcher launcher) {
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));

        for (CtClass c : classes) {
            ctClasses.put(c.getQualifiedName(), c);
            halsteadComplexityClassAnalyser(c);
        }

        n1 += distinctOperators.size();
        n1 += distinctAssgnOperators.size();
        n2 += distinctOperands.size();
        n2 += distinctAssgnOperands.size();


        for (Map.Entry dOperator : distinctOperators.entrySet()) {
            N1 += (int)dOperator.getValue();
        }

        for (Map.Entry dAssgnOperator : distinctAssgnOperators.entrySet()) {
            N1 += (int)dAssgnOperator.getValue();
        }

        for (Map.Entry dOperand : distinctOperands.entrySet()) {
            N2 += (int)dOperand.getValue();
        }

        for (Map.Entry dAssgnOperand : distinctAssgnOperands.entrySet()) {
            N2 += (int)dAssgnOperand.getValue();
        }


    }


    //
    public void halsteadComplexityClassAnalyser(CtClass c){

        for(CtOperatorAssignment assgnOperator : c.getElements(new TypeFilter<CtOperatorAssignment>(CtOperatorAssignment.class))){
            if(!distinctAssgnOperators.containsKey(assgnOperator.getKind().toString())){
                distinctAssgnOperators.put(assgnOperator.getKind().toString(), 1);
            }
            else{
                int freq = distinctAssgnOperators.get(assgnOperator.getKind().toString());
                distinctAssgnOperators.put(assgnOperator.getKind().toString(), freq + 1);
            }

            if(!distinctAssgnOperands.containsKey(assgnOperator.getAssigned().toString())){
                distinctAssgnOperands.put(assgnOperator.getAssigned().toString(), 1);
            }
            else{
                int freq = distinctAssgnOperands.get(assgnOperator.getAssigned().toString());
                distinctAssgnOperands.put(assgnOperator.getAssigned().toString(), freq + 1);
            }

        }

        for(CtUnaryOperator unOperator : c.getElements(new TypeFilter<CtUnaryOperator>(CtUnaryOperator.class))){
            if(!distinctOperators.containsKey(unOperator.getKind().toString())){
                distinctOperators.put(unOperator.getKind().toString(), 1);
            }
            else{
                int freq = distinctOperators.get(unOperator.getKind().toString());
                distinctOperators.put(unOperator.getKind().toString(), freq + 1);
            }

            if(!distinctOperands.containsKey(unOperator.getOperand().toString())){
                distinctOperands.put(unOperator.getOperand().toString(), 1);
            }
            else{
                int freq = distinctOperands.get(unOperator.getOperand().toString());
                distinctOperands.put(unOperator.getOperand().toString(), freq + 1);
            }

        }

        // Captures binary, bit
        for(CtBinaryOperator biOperator : c.getElements(new TypeFilter<CtBinaryOperator>(CtBinaryOperator.class))){
            if(!distinctOperators.containsKey(biOperator.getKind().toString())){
                distinctOperators.put(biOperator.getKind().toString(), 1);
            }
            else{
                int freq = distinctOperators.get(biOperator.getKind().toString());
                distinctOperators.put(biOperator.getKind().toString(), freq + 1);
            }

            if(!distinctOperands.containsKey(biOperator.getLeftHandOperand().toString())){
                distinctOperands.put(biOperator.getLeftHandOperand().toString(), 1);
            }
            else{
                int freq = distinctOperands.get(biOperator.getLeftHandOperand().toString());
                distinctOperands.put(biOperator.getLeftHandOperand().toString(), freq + 1);
            }

            if(!distinctOperands.containsKey(biOperator.getRightHandOperand().toString())){
                distinctOperands.put(biOperator.getRightHandOperand().toString(), 1);
            }
            else{
                int freq = distinctOperands.get(biOperator.getRightHandOperand().toString());
                distinctOperands.put(biOperator.getRightHandOperand().toString(), freq + 1);
            }

        }

        //Captures "?" tenuary operator
        for(CtConditional qMark : c.getElements(new TypeFilter<CtConditional>(CtConditional.class))){
            if(!distinctOperators.containsKey("COND")){
                distinctOperators.put("COND", 1);
            }
            else{
                int freq = distinctOperators.get("COND");
                distinctOperators.put("COND", freq + 1);
            }

            if(!distinctOperands.containsKey(qMark.getCondition().toString())){
                distinctOperands.put(qMark.getCondition().toString(), 1);
            }
            else{
                int freq = distinctOperands.get(qMark.getCondition().toString());
                distinctOperands.put(qMark.getCondition().toString(), freq + 1);
            }

            if(!distinctOperands.containsKey(qMark.getElseExpression().toString())){
                distinctOperands.put(qMark.getElseExpression().toString(), 1);
            }
            else{
                int freq = distinctOperands.get(qMark.getElseExpression().toString());
                distinctOperands.put(qMark.getElseExpression().toString(), freq + 1);
            }

            if(!distinctOperands.containsKey(qMark.getThenExpression().toString())){
                distinctOperands.put(qMark.getThenExpression().toString(), 1);
            }
            else{
                int freq = distinctOperands.get(qMark.getThenExpression().toString());
                distinctOperands.put(qMark.getThenExpression().toString(), freq + 1);
            }
        }

        //Captures "->" operator, when body is null, expression is the operand, visa versa
        for(CtLambda arrow : c.getElements(new TypeFilter<CtLambda>(CtLambda.class))){
            if(!distinctOperators.containsKey("LAMBDA")){
                distinctOperators.put("LAMBDA", 1);
            }
            else{
                int freq = distinctOperators.get("LAMBDA");
                distinctOperators.put("LAMBDA", freq + 1);
            }

            if(arrow.getExpression() != null){
                if(!distinctOperands.containsKey(arrow.getExpression().toString())){
                    distinctOperands.put(arrow.getExpression().toString(), 1);
                }
                else{
                    int freq = distinctOperands.get(arrow.getExpression().toString());
                    distinctOperands.put(arrow.getExpression().toString(), freq + 1);
                }
            }
            else if(arrow.getBody() != null){
                if(!distinctOperands.containsKey(arrow.getBody().toString())){
                    distinctOperands.put(arrow.getBody().toString(), 1);
                }
                else{
                    int freq = distinctOperands.get(arrow.getBody().toString());
                    distinctOperands.put(arrow.getBody().toString(), freq + 1);
                }
            }
        }
    }

    public ArrayList<Integer> getNumbers(){
        halsteadNumbers.add(n1);
        halsteadNumbers.add(n2);
        halsteadNumbers.add(N1);
        halsteadNumbers.add(N2);

        return halsteadNumbers;
    }



    public HashMap<String, String> getComplexityMeasures(){
        String programVocabulary;
        String programLength;
        String estProgramLength;
        String volume;
        String difficulty;
        String effort;
        String timeRequiredtoProgram;
        String deliveredBugs;

        try {

            programVocabulary = Integer.toString(n1 + n2);
            programLength = Integer.toString(N1 + N2);
            estProgramLength = Double.toString(n1 * (Math.log(n1) / Math.log(2)) + n2 * (Math.log(n2) / Math.log(2.0)));
            double volumeD = (N1 + N2) * (Math.log(n1 + n2) / Math.log(2));
            volume = Double.toString(volumeD);
            double difficultyD = (n1 / 2.0) * (N2 / n2);
            difficulty = Double.toString(difficultyD);
            effort = Double.toString(volumeD * difficultyD);
            timeRequiredtoProgram = Double.toString((volumeD * difficultyD) / 18.0);
            deliveredBugs = Double.toString(Math.pow((volumeD * difficultyD), 2.0 / 3.0) / 3000.0);

            complexityMeasures.put("Program vocabulary", programVocabulary);
            complexityMeasures.put("Program length", programLength);
            complexityMeasures.put("Estimated program length", estProgramLength);
            complexityMeasures.put("Volume", volume);
            complexityMeasures.put("Diffulty", difficulty);
            complexityMeasures.put("Effort", effort);
            complexityMeasures.put("Time required to program", timeRequiredtoProgram);
            complexityMeasures.put("Delivered bugs", deliveredBugs);

        }catch(Exception e){

        }


        return complexityMeasures;
    }
}
