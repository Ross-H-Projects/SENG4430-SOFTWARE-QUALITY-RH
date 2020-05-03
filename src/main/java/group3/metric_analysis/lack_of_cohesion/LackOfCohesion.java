// based on the description of cohesion here:
// https://help.semmle.com/wiki/pages/viewpage.action?pageId=29394037
// or find it on archive.is here: https://archive.is/Hc0TG


package group3;

import spoon.Launcher;
import spoon.reflect.code.*;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;


public class LackOfCohesion extends MetricAnalysis {
    private int n1; // # of pairs of distinct methods in a class that DO NOT have at-least one commonly-accessed fields
    private int n2; // # of pairs of distinct methods in a class that DO have at-least one commonly accessed fields

    private HashMap<String, CtClass<?>> ctClasses;
    private HashMap<String, Integer> classCohesionScores;
    private HashMap<String, HashMap<String, Boolean>> classMethodPairs;

    public LackOfCohesion () {
        n1 = 0;
        n2 = 0;
        ctClasses = new HashMap<String, CtClass<?>>();
        classCohesionScores = new HashMap<String, Integer>();
    }

    public void performAnalysis (Launcher launcher) {
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));

        for (CtClass<?> c : classes) {
            ctClasses.put(c.getQualifiedName(), c);
            classCohesionScores.put(c.getQualifiedName(), calculateCohesionForClass(c));
        }

    }

    private int calculateCohesionForClass (CtClass<?> currentClass) {
        // clear the method pairs data structure for the current class
        classMethodPairs = new HashMap<String, HashMap<String, Boolean>>();
        int noOfMethodPairsWithCohesion = 0;

        HashSet<String> classFieldNames = new HashSet<String>();
        List<CtField<?>> classFields = currentClass.getFields();
        for (CtField f : classFields) {
            classFieldNames.add(f.getSimpleName());
        }

        boolean methodPairExists;
        boolean methodPairCohesive;
        String methodASignature;
        String methodBSignature;
        for (CtMethod<?> methodA : currentClass.getMethods()) {
            methodASignature = methodA.getSignature();
            for (CtMethod<?> methodB : currentClass.getMethods()) {
                methodBSignature = methodB.getSignature();

                // don't check method against itself
                if (methodASignature.equals(methodBSignature)) {
                    continue;
                }

                methodPairExists = checkForMethodPair(methodASignature, methodBSignature);
                // don't check method pair coherence that have already been compared
                if (methodPairExists) {
                    continue;
                }

                createMethodPair(methodASignature, methodBSignature);
                methodPairCohesive = computeMethodPairCohesion(methodA, methodB, classFieldNames, currentClass.getQualifiedName());
                noOfMethodPairsWithCohesion += methodPairCohesive ? 1 : 0;
            }
        }

        System.out.println(currentClass.getSimpleName() + " :");
        for (String mA : classMethodPairs.keySet()) {
            for (String mB : classMethodPairs.get(mA).keySet()) {
                System.out.println("\t(" +mA + ", " + mB + ") = "  + classMethodPairs.get(mA).get(mB));
            }
        }
        System.out.println();

        return noOfMethodPairsWithCohesion;
    }

    private boolean computeMethodPairCohesion (CtMethod<?> methodA, CtMethod<?> methodB, HashSet<String> classFieldNames, String className) {
        HashSet<String> foundClassFieldReferences = new HashSet<String>();

        // get all class field references from methodA
        // mark them as found in foundClassFieldReferences
        CtBlock methodABody = methodA.getBody();
        checkMethodReferences(false, methodABody, classFieldNames, foundClassFieldReferences, className);

        // get all class field references from methodB
        // if there any (i.e. a marked as found in foundClassFieldReferences)
        // then mark the method pair as true in classMethodPairs
        CtBlock methodBBody = methodB.getBody();
        boolean res = checkMethodReferences(true, methodBBody, classFieldNames, foundClassFieldReferences, className);
        if (res) {
            classMethodPairs.get(methodA.getSignature()).put(methodB.getSignature(), true);
            return true;
        }

        return false;
    }

    private boolean checkMethodReferences (boolean returnImmediate, CtBlock block, HashSet<String> classFieldNames, HashSet<String> foundClassFieldReferences, String className) {
        HashSet<String> fieldsFoundThisCheck = new HashSet<String>();

        // check references to class fields in method body
        for (CtStatement line : block.getStatements()) {
            //System.out.println(line + ":\n");

            for (CtElement e : line.getElements(new TypeFilter<CtReference>(CtReference.class))) {
               if (!classFieldNames.contains(e.toString())) {
                   continue;
               }

               HashSet<String> referenceTypeStrings = new HashSet<String>();
               for (CtTypeReference tr : e.getReferencedTypes()) {
                   referenceTypeStrings.add(tr.toString());
               }

               if (!referenceTypeStrings.contains(className)) {
                   continue;
               }

               if (returnImmediate
                       && foundClassFieldReferences.contains(e.toString())
                       && !fieldsFoundThisCheck.contains(e.toString())) {
                   return true;
               }

               foundClassFieldReferences.add(e.toString());
               fieldsFoundThisCheck.add(e.toString());
//             System.out.println("\t" + e);
//             System.out.println("\t\t" + e.getParent().getElements(new TypeFilter<CtFieldAccess>(CtFieldAccess.class)));
//             System.out.println("\t\t" + referenceTypeStrings);
            }
        }

        return false;
    }

    private boolean checkForMethodPair (String methodA, String methodB) {
        boolean ret = false;

        try {
            classMethodPairs.get(methodA).get(methodB);
            ret = true;
        } catch (Exception e) {

        }

        try {
            classMethodPairs.get(methodB).get(methodA);
            ret = true;
        } catch (Exception e) {

        }

        return ret;
    }

    private void createMethodPair (String methodA, String methodB) {
        if (classMethodPairs.get(methodA) == null) {
            classMethodPairs.put(methodA, new HashMap<String, Boolean>());
        }

        if (classMethodPairs.get(methodA).get(methodB) == null) {
            classMethodPairs.get(methodA).put(methodB, false);
        }
    }
}
