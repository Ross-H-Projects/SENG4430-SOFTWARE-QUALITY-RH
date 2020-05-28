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
    private int classCouplingCounter;
    private HashMap<CtClass, Integer> cW;
    private HashMap<String, CtClass> classVars;

    public CouplingAnalysis(){
        visited_classes = new HashMap<String, Integer>();
        ctClasses = new HashMap<String, CtClass>();
        cW = new HashMap<CtClass, Integer>();
        couplingTotal = 0;
        classCouplingCounter = 0;
        classVars = new HashMap<String, CtClass>();
    }

    public void performAnalysis (Launcher launcher) {
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));
        HashMap<CtClass, Integer> couplingWeights = new HashMap<CtClass, Integer>();


        for (CtClass c : classes) {
            ctClasses.put(c.getQualifiedName(), c);
            cW.put(c, 0);
            //System.out.println("class: "+c.getQualifiedName());
        }


        for (CtClass c : classes) {
            if (!visited_classes.containsKey(c.getQualifiedName())) {

                classCouplingCounter = 0;
                classVars.clear();
                System.out.println("class: "+c.getQualifiedName());

                couplingClassAnalyser(c, classes);
                couplingTotal += classCouplingCounter;

                for  (Map.Entry mapElement : cW.entrySet()){
                     CtClass keyClass = (CtClass) mapElement.getKey();
                     String key = keyClass.getQualifiedName();

                    String value  = mapElement.getValue().toString();

                   // System.out.println("coupling counter: "+classCouplingCounter);
//                    System.out.println("Class: " + c.getQualifiedName() + " ---- " + value + " ---- Class: " + key);
//                    System.out.println("");
                }
                // Weighted Graph Double HashMap work in progress
                // HashMap<CtClass, HashMap<CtClass, Integer>> weightedCouplingGraph = new HashMap<CtClass, HashMap<CtClass, Integer>>();
                // weightedCouplingGraph.put(c, cW);
            }

        }

    }

    public int getCouplingTotal() { return couplingTotal; }

    public void couplingClassAnalyser(CtClass c, List<CtClass<?>> classList){
        for (CtClass cwClass : classList) {
            cW.replace((CtClass) cwClass, 0);
        }

        // implicit constructor call
        for (CtConstructorCall cc : c.getElements(new TypeFilter<CtConstructorCall>(CtConstructorCall.class))) {
            parameterCoupling(cc, c);
        }

        // explicit method call through an object reference
        for (CtInvocation in : c.getElements(new TypeFilter<CtInvocation>(CtInvocation.class))) {
            parameterCoupling(in, c);
        }

        // explicit static call
        for (CtTypeAccess typ : c.getElements(new TypeFilter<CtTypeAccess>(CtTypeAccess.class))){
            parameterCoupling(typ, c);
        }

        //inheritance coupling
        if (c.getSuperclass() != null) {
            //c.getSuperclass().getQualifiedName();
            int weight = cW.get(c.getSuperclass().getDeclaration());
            cW.replace((CtClass) c.getSuperclass().getDeclaration(), weight + 1);
            classCouplingCounter++;
        }

    }

    public void parameterCoupling(CtConstructorCall cc, CtClass c){

        if(cc.getType().getDeclaration() instanceof CtClass && !(cc.getType().getQualifiedName().equals(c.getQualifiedName()))){
            //System.out.println("\t" + (CtClass) cc.getType().getDeclaration());
            int weight = cW.get(cc.getType().getDeclaration());
            cW.replace((CtClass) cc.getType().getDeclaration(), weight + 1);
            classCouplingCounter++;
        }
    }

    public void parameterCoupling(CtInvocation in, CtClass c){
        try {

            //System.out.println("\t"+ in.getTarget().getType() +"\t || "+in);

            if(in.getTarget().getType().getDeclaration() instanceof CtClass && !(in.getTarget().getType().getQualifiedName().equals(c.getQualifiedName()))){

                int weight = cW.get(in.getTarget().getType().getDeclaration());
                cW.replace((CtClass) in.getTarget().getType().getDeclaration(), weight + 1);
                classCouplingCounter++;
            }

        } catch (Exception e) {
           // System.out.println("\t"+in);
        }

    }

    public void parameterCoupling(CtTypeAccess acc, CtClass c){
        try {
            if((acc.getAccessedType().getDeclaration() instanceof CtClass) && !(acc.getAccessedType().getQualifiedName().equals(c.getQualifiedName()))){
                int weight = cW.get(acc.getAccessedType().getDeclaration());
                cW.replace((CtClass) acc.getAccessedType().getDeclaration(), weight + 1);
                classCouplingCounter++;
            }
        } catch (Exception e) {

        }
    }

    public void inheritanceCoupling(CtClass c){

        // Capturing direct inheritance coupling; child class is coupled to parent
        if (c.getSuperclass() != null) {
            c.getSuperclass().getQualifiedName();
            int weight = cW.get(c.getSuperclass().getDeclaration());
            cW.replace((CtClass) c.getSuperclass().getDeclaration(), weight + 1);

        }
    }



}


