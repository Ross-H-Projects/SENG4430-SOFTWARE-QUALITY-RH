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
        couplingWeights = new HashMap<CtClass, Integer>();
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

        CouplingReturn x = new CouplingReturn();
        return x;
    }

    public int couplingClassAnalyser(CtClass c, HashMap<CtClass, Integer> cW){

        int couplingCounter = 0;

        //6 styles of coupling (inter-class relationship)
        //Paramtere coupling -  constructors, explicit method call through object reference,
        //                      explicit static method call

        List<CtField> fields = c.getFields();
        /*
        Set<CtConstructor> constructorList = c.getConstructors();
        for(CtConstructor constructor : constructorList) {
            List<CtElement> constructorChildren = constructor.getDirectChildren();

            for (CtElement children : constructorChildren) {
                System.out.println("Children: " + children.toString());
            }

            //System.out.println("Constructor: " + constructor.toString());
        }
        */
        /*
        for(CtField field : fields){
            System.out.println("field Type: " + field.getType().toString());
           // if(field.getType().toString()){

          //  }
        }
        */

        Set<CtMethod> methodList = new HashSet<CtMethod>();
        methodList = c.getMethods();
        for(CtMethod method : methodList){
            System.out.println("Method: " + method.getType().toString());

            CtBlock methodBlock = method.getBody();
            List<CtStatement> statementList = methodBlock.getStatements();
            for(CtStatement statement : statementList) {
                System.out.println("statement type: " + statement.getReferencedTypes().toString());
                Set<CtTypeReference<?>> statementRefTypes = statement.getReferencedTypes();
                for(CtTypeReference<?> refType : statementRefTypes){
                    if(refType.getDeclaration() instanceof CtClass){
                        for(CtClass coupledClass : )
                            couplingCounter++;

                    }
                }

            }

            System.out.println("Coupling Score: " + couplingCounter);



        }

       // List<CtStatement> methodStatements = methodBody.getElements(new TypeFilter(CtStatement.class));
       // for(CtStatement s : methodStatements){
       //     System.out.println(s);
       // }




        //Inheritance Coupling

        //Global Coupling - public and protected variables being called

        //Abstraction Coupling - abstract class method being called

        //Import and Export Coupling

        //External Coupling

        return 0;
    }

}
