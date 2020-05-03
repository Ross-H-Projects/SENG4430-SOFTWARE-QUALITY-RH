package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.*;
import spoon.reflect.code.*;
import spoon.reflect.visitor.*;



public class CouplingAnalysis extends MetricAnalysis {

    private HashMap<String, CtElement> firstParse;
    private HashMap<String, Integer> visited_classes;
    private HashMap<String, CtClass> ctClasses;
    private HashMap<CtClass, Integer> couplingWeights;

    public CouplingAnalysis() {
        firstParse = new HashMap<String, CtElement>();
        visited_classes = new HashMap<String, Integer>();
        ctClasses = new HashMap<String, CtClass>();
        couplingWeights = new HashMap<CtClass, Integer>();      //Coupling counter for each class, irrespective of pairings
    }

    public MetricReturn performAnalysis (String filename){
        Launcher launcher = Utilities.importCodeSample(filename);
        List<CtClass> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass>(CtClass.class));

        for (CtClass c : classes) {
            ctClasses.put(c.getQualifiedName(), c);
            couplingWeights.put(c, 0);
        }

        int CBOtotal = 0;
        int CBOcounter = 0;

        for (CtClass c : classes) {
            if (!visited_classes.containsKey(c.getQualifiedName())) {
                CBOcounter = couplingClassAnalyser(c, couplingWeights);

            }

            CBOtotal += CBOcounter;
        }

        System.out.println("Total program coupling: " + CBOtotal);

        CouplingReturn x = new CouplingReturn();
        return x;
    }

    public int couplingClassAnalyser(CtClass c,  HashMap<CtClass, Integer> cW){

        int couplingCounter = 0;
        boolean check = false;

        //6 styles of coupling (inter-class relationship)
        //Paramtere coupling -  constructors, explicit method call through object reference,
        //                      explicit static method call

        //Capturing class references in the field outside of methods and constructors

        List<CtField> fields = c.getFields();
        for (CtField field: fields){
            if(field.getType().getDeclaration() instanceof CtClass){
                int weight = cW.get(field.getType().getDeclaration());
                cW.replace((CtClass) field.getType().getDeclaration(), weight + 1);

                couplingCounter++;
                check = true;
            }

           // System.out.println("class: " + c.getQualifiedName() + " | statement: " + field.toString());
            check = false;

            for(Map.Entry<CtClass, Integer> cWEntry : cW.entrySet()){

               // System.out.println( "class: " + c.getQualifiedName() + " -> weight: " + cWEntry.getValue() + " class: " + cWEntry.getKey().getQualifiedName());
            }
        }

        //Capturing class references inside of constructors

        Set<CtConstructor> constructors = c.getConstructors();
        for (CtConstructor constructor: constructors){
            CtBlock constrBody = constructor.getBody();
            List<CtStatement> constrStatementList = constrBody.getStatements();

            for(CtStatement constrStatement : constrStatementList) {

                Set<CtTypeReference<?>> constrRefTypes = constrStatement.getReferencedTypes();

                for(CtTypeReference<?> constrRefType : constrRefTypes) {

                    if (constrRefType.getDeclaration() instanceof CtClass) {

                        if((constrRefType.getDeclaration()).equals(c.getQualifiedName()) == false) {

                            int weight = cW.get(constrRefType.getDeclaration());
                            cW.replace((CtClass) constrRefType.getDeclaration(), weight + 1);

                            couplingCounter++;
                            check = true;

                            for (Map.Entry<CtClass, Integer> cWEntry : cW.entrySet()) {

                              //  System.out.println("class: " + c.getQualifiedName() + " -> weight: " + cWEntry.getValue() + " class: " + cWEntry.getKey().getQualifiedName());
                            }
                        }
                    }




                }

              //  System.out.println("class: " + c.getQualifiedName() + " | statement: " + constrStatement.toString());
                check = false;

            }
        }

        //Capturing class references inside of methods

        Set<CtMethod> methodList = c.getMethods();
        for(CtMethod method : methodList){

            List<CtInvocation> invocations = Query.getElements(method, new TypeFilter(CtInvocation.class));

            for (CtInvocation invocation : invocations) {
                Set<CtTypeReference<?>> invocationRefTypes = invocation.getReferencedTypes();

                for(CtTypeReference<?> invocRefType : invocationRefTypes){
                    if(invocRefType.getDeclaration() instanceof CtClass){
                      //  System.out.println("class: " + invocRefType.getQualifiedName() + " -> method: " + method.toString());
                    }
                }
            }


            CtBlock methodBlock = method.getBody();
            List<CtStatement> statementList = methodBlock.getStatements();
            for(CtStatement methStatement : statementList) {
                if(methStatement instanceof CtLocalVariable){
                    System.out.println("YES"+methStatement);
                }

                List<CtElement> methStatChildren = methStatement.getDirectChildren();

                for(CtElement methStatChild : methStatChildren){
                    Set<CtTypeReference<?>> childTypes = methStatChild.getReferencedTypes();
                    for(CtTypeReference<?> childType : childTypes){
                        if(childType.getDeclaration() instanceof CtClass){
                              System.out.println("class: " + childType.getQualifiedName() + " -> child: " + methStatChild.toString());
                        }
                    }
                   // System.out.println("Child: " + methStatChild.toString() + " - Statement: " + methStatChild.toString());
                }

                Set<CtTypeReference<?>> statementRefTypes = methStatement.getReferencedTypes();

                for(CtTypeReference<?> refType : statementRefTypes){
                    if(refType.getDeclaration() instanceof CtClass){

                        int weight = cW.get(refType.getDeclaration());
                        cW.replace((CtClass) refType.getDeclaration(), weight + 1);

                       // System.out.println("type declaration: "+ (CtClass) refType.getDeclaration());
                        couplingCounter++;
                        check = true;
                    }

                    //System.out.println("Type recs: " + refType.getTypeDeclaration());
                }

               // System.out.println("class: " + c.getQualifiedName() + " | statement: " + methStatement.toString());
                check = false;

            }
            /*
            System.out.println("Total Coupling Score: " + couplingCounter);
            for(int cWEntry : cW.values()){
                System.out.println("entry: " + cWEntry);
            }
            */
            for(Map.Entry<CtClass, Integer> cWEntry : cW.entrySet()){

               // System.out.println( "class: " + c.getQualifiedName() + " -> weight: " + cWEntry.getValue() + " class: " + cWEntry.getKey().getQualifiedName());
            }


        }

        //Stores the pairing of each weighted edge connected to another class with currently analysed class
        HashMap<CtClass, HashMap<CtClass, Integer>> weightedCouplingGraph = new HashMap<CtClass, HashMap<CtClass, Integer>>();
        weightedCouplingGraph.put(c, cW);







        //Inheritance Coupling

        //Global Coupling - public and protected variables being called

        //Abstraction Coupling - abstract class method being called

        //Import and Export Coupling

        //External Coupling


        return couplingCounter;
    }



}
