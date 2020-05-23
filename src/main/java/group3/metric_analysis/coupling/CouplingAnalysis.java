package group3.metric_analysis.coupling;

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

//Restricted to parameter coupling in this demo

public class CouplingAnalysis extends MetricAnalysis {
    private HashMap<String, Integer> visited_classes;
    private HashMap<String, CtClass> ctClasses;

    private int couplingTotal;

    public CouplingAnalysis(){
        visited_classes = new HashMap<String, Integer>();
        ctClasses = new HashMap<String, CtClass>();

    }

    public void performAnalysis (Launcher launcher) {
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));
        HashMap<CtClass, Integer> couplingWeights = new HashMap<CtClass, Integer>();

        for (CtClass c : classes) {
            ctClasses.put(c.getQualifiedName(), c);
            couplingWeights.put(c, 0);
            System.out.println("class: "+c.getQualifiedName());
        }


        for (CtClass c : classes) {
            if (!visited_classes.containsKey(c.getQualifiedName())) {

                HashMap<CtClass, Integer> couplingWeightsGiven = couplingClassAnalyser(c, couplingWeights, classes);

                for  (Map.Entry mapElement : couplingWeightsGiven.entrySet()){
                     CtClass keyClass = (CtClass) mapElement.getKey();
                     String key = keyClass.getQualifiedName();

                    String value  = mapElement.getValue().toString();

                   // System.out.println("Class: " + c.getQualifiedName() + " ---- " + value + " ---- Class: " + key);
                   // System.out.println("");
                }
                // Weighted Graph Double HashMap work in progress
                // HashMap<CtClass, HashMap<CtClass, Integer>> weightedCouplingGraph = new HashMap<CtClass, HashMap<CtClass, Integer>>();
                // weightedCouplingGraph.put(c, cW);
            }

        }


    }

    public int getCouplingTotal() { return couplingTotal; }


    public HashMap<CtClass, Integer> couplingClassAnalyser(CtClass c, HashMap<CtClass, Integer> cW, List<CtClass<?>> classList){

        for (CtClass cwClass : classList) {
            cW.replace((CtClass) cwClass, 0);
        }

       // System.out.println("CLASS->"+c.getQualifiedName());

        HashMap<String, String> variableClassMap = new HashMap<String, String>();

        int couplingCounter = 0;

        //Capturing class references in the field outside of methods and constructors

        List<CtField> fields = c.getFields();
        for (CtField field: fields){
            if(field.getType().getDeclaration() instanceof CtClass){

                int weight = cW.get(field.getType().getDeclaration());
                cW.replace((CtClass) field.getType().getDeclaration(), weight + 1);
                couplingCounter++;
            }
        }

        //Capturing implicit constructor calls "inside" of constructors

        Set<CtConstructor> constructors = c.getConstructors();
        for (CtConstructor constructor: constructors){
            CtBlock constrBody = constructor.getBody();
            List<CtStatement> constrStatementList = constrBody.getStatements();

            for(CtStatement constrStatement : constrStatementList) {

                Set<CtTypeReference<?>> constrRefTypes = constrStatement.getReferencedTypes();

                for(CtTypeReference<?> constrRefType : constrRefTypes) {

                    if (constrRefType.getDeclaration() instanceof CtClass) {

                        // Excludes class self-reference
                        if((constrRefType.getDeclaration()).equals(c.getQualifiedName()) == false) {

                            int weight = cW.get(constrRefType.getDeclaration());
                            cW.replace((CtClass) constrRefType.getDeclaration(), weight + 1);
                            couplingCounter++;
                        }
                    }
                }
            }
        }

        //Capturing implicit constructor calls inside of methods

        /*
        Set<CtMethod> methodList = c.getMethods();
        for(CtMethod method : methodList) {


            //List of local variables in the same method as their constructors
            List<CtLocalVariable<?>> constructorVars = method.getElements(new TypeFilter<CtLocalVariable<?>>(CtLocalVariable.class));
            if (!constructorVars.isEmpty()) {





                for (int i = 0; i < constructorVars.size(); i++) {
                    System.out.println("var number: " + i + " var: "+constructorVars.get(i).toString());


                    if(constructorVars.get(i).getType().getDeclaration() != null) {
                        CtMethod parentMethod = constructorVars.get(i).getParent(CtMethod.class);
                        List<CtConstructorCall<?>> corrConstr = parentMethod.getElements(new TypeFilter<CtConstructorCall<?>>(CtConstructorCall.class));
                        System.out.println("size: "+corrConstr.size());
                       // System.out.println("corrConstr: " + corrConstr.get(i).getType().getDeclaration().toString());

                        for (int j = 0; j < corrConstr.size(); j++) {
                            System.out.println("constructor: "+corrConstr.get(j).toString());
                        }

                        if(corrConstr.get(i).getType().getDeclaration().toString().equals(null)) {

                            if (!corrConstr.isEmpty()) {
                                variableClassMap.put(constructorVars.get(i).getSimpleName(), corrConstr.get(i).getType().getSimpleName());
                                System.out.println("method: " + method.getSimpleName() + "class: " + c.getQualifiedName());

                                System.out.println("line: " + constructorVars.get(i).getType().getDeclaration());

                                int weight = cW.get(constructorVars.get(i).getType().getDeclaration());
                                cW.replace((CtClass) constructorVars.get(i).getType().getDeclaration(), weight + 1);
                                couplingCounter++;
                            }
                        }
                    }

                }
            }
        }

        // Capturing explicit calls through object reference inside methods (including main)

        for(CtMethod mMethod : methodList){
            List<CtInvocation<?>> methodCalls = mMethod.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
            if(!methodCalls.isEmpty()) {
                for(CtInvocation methodCall : methodCalls){

                    if(methodCall.getExecutable().getDeclaringType().getSimpleName() != null) {

                        String key = methodCall.getExecutable().getDeclaringType().getSimpleName();

                        // System.out.println("key: "+key);

                        if (!key.equals("PrintStream")) {

                            try {
                                String corrClass = variableClassMap.get(key);

                                System.out.println("corclass: " + variableClassMap.get(key));

                                for (CtClass cClass : classList) {
                                    if (corrClass.equals(cClass.getQualifiedName())) {
                                        int weight = cW.get(cClass);
                                        cW.replace((CtClass) cClass, weight + 1);
                                        couplingCounter++;
                                    }
                                }
                            } catch(NullPointerException e){
                                System.out.print("KEY: "+ key+ " NullPointerException Caught");
                            }
                        }
                    }
                }
            }
        }

        // Capturing direct inheritance coupling; child class is coupled to parent
        if (c.getSuperclass() != null) {
            c.getSuperclass().getQualifiedName();
            int weight = cW.get(c.getSuperclass().getDeclaration());
            cW.replace((CtClass) c.getSuperclass().getDeclaration(), weight + 1);
            couplingCounter++;
        }
`       */

        Set<CtMethod> methodList = c.getMethods();
        for(CtMethod method : methodList) {
            System.out.println("method: "+method.getSimpleName());
            CtBlock<?> methodBody =  method.getBody();
            List<CtStatement> methodStatements = methodBody.getStatements();

            int i = 0;

                for(CtElement element :  methodStatements.get(0).getElements(new TypeFilter<CtInvocation>(CtInvocation.class))){
                    System.out.println("class "+c.getQualifiedName()+" reference element "+i+" :"+element.toString());
                    i++;
                }
            i = 0;

            List<CtLocalVariable<?>> mLocalVars = method.getElements(new TypeFilter<CtLocalVariable<?>>(CtLocalVariable.class));
            List<CtConstructorCall<?>> mConstructors = method.getElements(new TypeFilter<CtConstructorCall<?>>(CtConstructorCall.class));


            List<CtInvocation<?>> mInvocations = method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));

            /*
            System.out.println("class "+c.getQualifiedName()+" method "+method.getSimpleName()+" var  "+mLocalVars.get(0).getType());
            System.out.println("class "+c.getQualifiedName()+" method "+method.getSimpleName()+" var  "+mLocalVars.get(1).getType());
            System.out.println("class "+c.getQualifiedName()+" method "+method.getSimpleName()+" var  "+mLocalVars.get(2).getType());
            System.out.println("class "+c.getQualifiedName()+" method "+method.getSimpleName()+" var  "+mLocalVars.get(3).getType());

            System.out.println("class "+c.getQualifiedName()+" method "+method.getSimpleName()+" inv  "+mInvocations.get(0).getType());
            System.out.println("class "+c.getQualifiedName()+" method "+method.getSimpleName()+" inv  "+mInvocations.get(1).getType());
            System.out.println("class "+c.getQualifiedName()+" method "+method.getSimpleName()+" inv  "+mInvocations.get(2).getType());
            System.out.println("class "+c.getQualifiedName()+" method "+method.getSimpleName()+" inv  "+mInvocations.get(3).getType());
            */

           // for (int j = 0; j < mInvocations.size(); j++) {
           //     System.out.println("class "+c.getQualifiedName()+" invocation: "+mInvocations.get(j).toString());
           // }
        }




        couplingTotal += couplingCounter;

        return cW;
    }



}


