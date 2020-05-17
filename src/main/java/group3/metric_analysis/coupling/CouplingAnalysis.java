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

        Set<CtMethod> methodList = c.getMethods();
        for(CtMethod method : methodList) {

            List<CtLocalVariable<?>> constructorVars = method.getElements(new TypeFilter<CtLocalVariable<?>>(CtLocalVariable.class));
            if (!constructorVars.isEmpty()) {

                for (int i = 0; i < constructorVars.size(); i++) {
                    CtMethod parentMethod = constructorVars.get(i).getParent(CtMethod.class);
                    List<CtConstructorCall<?>> corrConstr = parentMethod.getElements(new TypeFilter<CtConstructorCall<?>>(CtConstructorCall.class));

                    if (!corrConstr.isEmpty()) {
                        variableClassMap.put(constructorVars.get(i).getSimpleName(), corrConstr.get(i).getType().getSimpleName());

                        int weight = cW.get(constructorVars.get(i).getType().getDeclaration());
                        cW.replace((CtClass) constructorVars.get(i).getType().getDeclaration(), weight + 1);
                        couplingCounter++;
                    }
                }
            }
        }

        // Capturing explicit calls through object reference inside methods (including main)

        for(CtMethod mMethod : methodList){
            List<CtInvocation<?>> methodCalls = mMethod.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
            if(!methodCalls.isEmpty()) {
                for(CtInvocation methodCall : methodCalls){
                    String key = methodCall.getExecutable().getDeclaringType().getSimpleName();

                   // System.out.println("key: "+key);

                    if(!key.equals("PrintStream")) {
                        String corrClass = variableClassMap.get(key);

                    //    System.out.println("corclass: " + variableClassMap.get(key));

                        for (CtClass cClass : classList) {
                            if (corrClass.equals(cClass.getQualifiedName())) {
                                int weight = cW.get(cClass);
                                cW.replace((CtClass) cClass, weight + 1);
                                couplingCounter++;
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



        couplingTotal += couplingCounter;

        return cW;
    }



}


