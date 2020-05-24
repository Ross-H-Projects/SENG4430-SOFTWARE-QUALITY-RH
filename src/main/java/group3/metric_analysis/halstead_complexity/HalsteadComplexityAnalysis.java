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
    private HashMap<String, String> complexityMeasures;
    private HashMap<String, CtClass> ctClasses;


    public HalsteadComplexityAnalysis() {
        n1 = 0;                             //n1 = the number of distinct operators
        n2 = 0;                             //n2 = the number of distinct operands
        N1 = 0;                             //N1 = the total number of operators
        N2 = 0;                             //N2 = the total number of operands

        ctClasses = new HashMap<String, CtClass>();
        complexityMeasures = new HashMap<String, String>();
    }

    public void performAnalysis (Launcher launcher) {
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));

        for (CtClass c : classes) {
            ctClasses.put(c.getQualifiedName(), c);
            System.out.println("class: "+c.getQualifiedName());
            halsteadComplexityClassAnalyser(c);
        }

        System.out.println("n1: "+n1+" n2: "+n2+" N1: "+N1+" N2: "+N2+"\n");


    }



    public void halsteadComplexityClassAnalyser(CtClass c){
        List<CtField<?>> classFields = c.getFields();
        for (CtField cF : classFields) {
            fieldAnalyser(cF);
        }

        Set<CtConstructor<?>> classConstrutors = c.getConstructors();
        for (CtConstructor cC : classConstrutors) {
            constructorAnalyser(cC);
        }

        Set<CtMethod<?>> classMethods = c.getMethods();
        for (CtMethod cM : classMethods) {
            System.out.println("method: "+cM.getSimpleName());
            methodAnalyser(cM);
        }




    }

    public void fieldAnalyser(CtField classField){

    }

    public void constructorAnalyser(CtConstructor classConstructor){

    }

    public void methodAnalyser(CtMethod classMethod){

        //Stores distinct enums and will be traversed whenever an operator is come across to check if it has been accounuted for
        ArrayList<String> distinctOperators = new ArrayList<String>();
        ArrayList<String> distinctOperands = new ArrayList<String>();


        for (CtStatement line : classMethod.getBody().getStatements()){

            List<CtCodeElement> codeElements = line.getElements(new TypeFilter<CtCodeElement>(CtCodeElement.class));
            for(CtCodeElement cElement : codeElements){
                System.out.println("code Element: "+cElement.toString());

                for(CtUnaryOperator unOperator : cElement.getElements(new TypeFilter<CtUnaryOperator>(CtUnaryOperator.class))){
                    if(!distinctOperators.contains(unOperator.getKind().toString())){
                        distinctOperators.add(unOperator.getKind().toString());
                    }

                    if(!distinctOperands.contains(unOperator.getOperand().toString())){
                        distinctOperands.add(unOperator.getOperand().toString());
                    }

                    N1++;
                    N2++;
                }

                for(CtBinaryOperator biOperator : cElement.getElements(new TypeFilter<CtBinaryOperator>(CtBinaryOperator.class))){
                    if(!distinctOperators.contains(biOperator.getKind().toString())){
                        distinctOperators.add(biOperator.getKind().toString());
                    }

                    if(!distinctOperands.contains(biOperator.getLeftHandOperand().toString())){
                        distinctOperands.add(biOperator.getLeftHandOperand().toString());
                    }

                    if(!distinctOperands.contains(biOperator.getRightHandOperand().toString())){
                        distinctOperands.add(biOperator.getRightHandOperand().toString());
                    }

                    N1++;
                    N2+=2;      //left and right operands
                }

                for(CtOperatorAssignment asgnOperator : cElement.getElements(new TypeFilter<CtOperatorAssignment>(CtOperatorAssignment.class))){
                    if(!distinctOperators.contains(asgnOperator.getKind().toString())){

                        distinctOperators.add(asgnOperator.getKind().toString());
                    }


                    N1++;
                }
            }




            /*
            List<CtLocalVariable> codeElements = line.getElements(new TypeFilter<CtLocalVariable>(CtLocalVariable.class));
            for(CtLocalVariable cElement : codeElements){
                System.out.println("variable : " + cElement);

                CtTypeReference cElementTypes = cElement.getType();
               // for (CtTypeReference cElementType : cElementTypes) {
                        System.out.println("type: " + cElementTypes);
               // }

                //switch () {
                  //  case "inheritance_depth":




                    //List<CtLocalVariable> idents = cElement.getElements(new TypeFilter<CtLocalVariable>(CtLocalVariable.class));
                    //for (CtLocalVariable ident : idents) {
                    //    System.out.println("identifiers: " + ident);
                   // }
                //}

            }
            System.out.println("");
            */

        }
        System.out.println("distinct operators: "+Arrays.toString(distinctOperators.toArray()));
        System.out.println("distinct operands: "+Arrays.toString(distinctOperands.toArray())+"\n");

        n1 += distinctOperators.size();
        n2 += distinctOperands.size();
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

        programVocabulary = Integer.toString(n1 + n2);
        programLength = Integer.toString(N1 + N2);
        estProgramLength = Double.toString(n1*(Math.log(n1)/Math.log(2)) + n2*(Math.log(n2)/Math.log(2)));
        double volumeD = (N1 + N2)*(Math.log(n1 + n2)/Math.log(2));
        volume = Double.toString(volumeD);
        double difficultyD = (n1/2)*(N2/n2);
        difficulty = Double.toString(difficultyD);
        effort = Double.toString(volumeD*difficultyD);
        timeRequiredtoProgram = Double.toString((volumeD*difficultyD)/18);
        deliveredBugs = Double.toString(Math.pow((volumeD*difficultyD),2/3)/3000);

        complexityMeasures.put("Program vocabulary", programVocabulary);
        complexityMeasures.put("Program length", programLength);
        complexityMeasures.put("Estimated program length", estProgramLength);
        complexityMeasures.put("Volume", volume);
        complexityMeasures.put("Diffulty", difficulty);
        complexityMeasures.put("Effort", effort);
        complexityMeasures.put("Time required to program", timeRequiredtoProgram);
        complexityMeasures.put("Delivered bugs", deliveredBugs);


        return complexityMeasures;
    }
}
