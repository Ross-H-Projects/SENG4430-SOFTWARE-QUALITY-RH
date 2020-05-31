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


    private int classCouplingCounter;
    private HashMap<CtClass, Integer> cW;

    private int couplingTotal;
    private HashMap<CtClass, HashMap<CtClass, Integer>> weightedCouplingGraph;

    public CouplingAnalysis(){
        visited_classes = new HashMap<String, Integer>();
        ctClasses = new HashMap<String, CtClass>();

        classCouplingCounter = 0;
        cW = new HashMap<CtClass, Integer>();

        couplingTotal = 0;
        weightedCouplingGraph = new HashMap<CtClass, HashMap<CtClass, Integer>>();
    }

    public void performAnalysis (Launcher launcher) {
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));
        HashMap<CtClass, Integer> couplingWeights = new HashMap<CtClass, Integer>();


        for (CtClass c : classes) {
            ctClasses.put(c.getQualifiedName(), c);
            cW.put(c, 0);
        }


        for (CtClass c : classes) {
            // reset auxillary class weight map
            cW = new HashMap<CtClass, Integer>();
            for (Map.Entry <String, CtClass> entry : ctClasses.entrySet()){
                cW.put(entry.getValue(), 0);
            }

            if (!visited_classes.containsKey(c.getQualifiedName())) {

                classCouplingCounter = 0;

                couplingClassAnalyser(c, classes);
                couplingTotal += classCouplingCounter;

                // Weighted Graph Double HashMap
                weightedCouplingGraph.put(c, cW);

           }


        }
    }

    public HashMap<CtClass, HashMap<CtClass, Integer>> getCouplingWeightGraph() {
        return weightedCouplingGraph;
    }

    public Integer getCouplingTotal() {
        return couplingTotal;
    }


    public void couplingClassAnalyser(CtClass c, List<CtClass<?>> classList){
//        for (CtClass cwClass : classList) {
//            cW.replace((CtClass) cwClass, 0);
//        }

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

    }

    public void parameterCoupling(CtConstructorCall cc, CtClass c){

        if(cc.getType().getDeclaration() instanceof CtClass && !(cc.getType().getQualifiedName().equals(c.getQualifiedName()))){
            int weight = cW.get(cc.getType().getDeclaration());
            cW.replace((CtClass) cc.getType().getDeclaration(), weight + 1);
            classCouplingCounter++;
        }
    }

    public void parameterCoupling(CtInvocation in, CtClass c){
        try {

            if(in.getTarget().getType().getDeclaration() instanceof CtClass && !(in.getTarget().getType().getQualifiedName().equals(c.getQualifiedName()))){

                int weight = cW.get(in.getTarget().getType().getDeclaration());
                cW.replace((CtClass) in.getTarget().getType().getDeclaration(), weight + 1);
                classCouplingCounter++;
            }

        } catch (Exception e) {

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




}


