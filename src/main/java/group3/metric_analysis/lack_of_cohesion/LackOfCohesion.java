// based on the description of cohesion here:
// https://help.semmle.com/wiki/pages/viewpage.action?pageId=29394037
// or find it on archive.is here: https://archive.is/Hc0TG


package group3.metric_analysis.lack_of_cohesion;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.code.*;
import spoon.reflect.declaration.*;
import spoon.reflect.reference.CtReference;
import spoon.reflect.reference.CtTypeReference;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;


public class LackOfCohesion extends MetricAnalysis {

    private HashMap<String, CtClass<?>> ctClasses;
    private HashMap<String, Integer> classCohesionScores;
    private HashMap<String, Float> classCohesionRatios;
    private HashMap<String, HashMap<String, Boolean>> classMethodPairs;

    public LackOfCohesion () {
        ctClasses = new HashMap<String, CtClass<?>>();
        classCohesionScores = new HashMap<String, Integer>();
        classCohesionRatios = new HashMap<String, Float>();
    }

    public HashMap<String, Integer> getClassCohesionScores () {
        return classCohesionScores;
    }

    public HashMap<String, Float> getClassCohesionRatios () {
        return classCohesionRatios;
    }

    public HashMap<String, CtClass<?>> getClasses () {
        return ctClasses;
    }

    public void performAnalysis (Launcher launcher) {
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));

        int classCohesionScore;
        float classCohesionRatio;
        int noOfDistinctMethodPairsWithCohesion;
        int noOfDistinctMethodPairsInClass;
        for (CtClass<?> c : classes) {
            ctClasses.put(c.getQualifiedName(), c);

            // don't attempt to calculate cohesion when there are not
            // an adequate amount of method pairs, just mark them as the best scores
            if (c.getMethods().size() <= 1) {
                classCohesionScores.put(c.getQualifiedName(), 0);
                classCohesionRatios.put(c.getQualifiedName(), 1.0f);
                continue;
            }

            noOfDistinctMethodPairsWithCohesion = calculateCohesionForClass(c);

            // the no of distinct method pairs in the class can be
            // seen as the amount of edges in a complete graph where each node, n,
            // represents a method and the edges represent method pairs
            // so we can calculate the distinct amount of method pairs by the formula: n(n-1)/2
            noOfDistinctMethodPairsInClass = c.getMethods().size();
            noOfDistinctMethodPairsInClass = (noOfDistinctMethodPairsInClass * (noOfDistinctMethodPairsInClass - 1)) / 2;

            // the class cohesion score is being calculated by taking the formula provided in the article (linked at the top of the document)
            // and adapting it so that it works for distinct pairs, instead of doubly counted pairs
            // i.e. max((n1 - n2)/2, 0) = max(((noOfDistinctMethodPairs - noOfDistinctMethodPairsWithCohesion) - noOfDistinctMethodPairsWithCohesion), 0)
            //      => max((n1 - n2)/2, 0) = max((noOfDistinctMethodPairs - 2 * noOfDistinctMethodPairsWithCohesion), 0)
            classCohesionScore = noOfDistinctMethodPairsInClass - 2 * noOfDistinctMethodPairsWithCohesion;
            classCohesionScore = Math.max(classCohesionScore, 0);

            // we also calculate the ratio of no of distinct method pairs of cohesion / no of distinct method pairs
            classCohesionRatio = (float) noOfDistinctMethodPairsWithCohesion / (float) noOfDistinctMethodPairsInClass;

            classCohesionScores.put(c.getQualifiedName(), classCohesionScore);
            classCohesionRatios.put(c.getQualifiedName(), classCohesionRatio);
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

                // don't check abstract method or compare abstract methods with cocnrete methods
                if (methodA.isAbstract() || methodB.isAbstract()) {
                    continue;
                }

                // don't check method against itself
                if (methodASignature.equals(methodBSignature)) {
                    continue;
                }

                methodPairExists = checkForMethodPair(methodASignature, methodBSignature);
                // don't check method pair coherence that have already been compared
                if (methodPairExists) {
                    continue;
                }

                // go on to create the method pair
                createMethodPair(methodASignature, methodBSignature);
                // get whether or not the method pairs are cohesive
                methodPairCohesive = computeMethodPairCohesion(methodA, methodB, classFieldNames, currentClass.getQualifiedName());
                // if they are cohesive, increment the amount of method pairs that are cohesive
                noOfMethodPairsWithCohesion += methodPairCohesive ? 1 : 0;
            }
        }

//        System.out.println(currentClass.getSimpleName() + " :");
//        //System.out.println(classMethodPairs.keySet());
//        for (String mA : classMethodPairs.keySet()) {
//            //System.out.println(classMethodPairs.get(mA).keySet());
//            for (String mB : classMethodPairs.get(mA).keySet()) {
//                System.out.println("\t(" +mA + ", " + mB + ") = "  + classMethodPairs.get(mA).get(mB));
//            }
//        }
//        System.out.println();

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

        try {
            int a = 0;
            for (CtStatement line : block.getStatements()) {
                a++;
            }
        } catch (Exception e) {
            int j = 2;
        }

        // check references to class fields in method body
        // look line by line
        for (CtStatement line : block.getStatements()) {

            // for every element in the line that is a reference to some class
            for (CtElement e : line.getElements(new TypeFilter<CtReference>(CtReference.class))) {

                // make sure the element we are inspecting has some association with our class field names
                // also by checking e.toString() is in class field names we narrow down
                // elements to be single variables, so we avoid attempting to process elements that are partials of the line
                if (!classFieldNames.contains(e.toString())) {
                    continue;
                }

                // get the reference types for the element, this would be the classes the elements belong to
                HashSet<String> referenceTypeStrings = new HashSet<String>();
                for (CtTypeReference tr : e.getReferencedTypes()) {
                    referenceTypeStrings.add(tr.toString());
                }

                // make sure the variable reference we are looking at is actually referencing to
                // the class we are processing.
                if (!referenceTypeStrings.contains(className)) {
                   continue;
                }

                // if we are looking at the 2nd method in the pair (this is denoted
                // by the return immediate flag), and we have already found this class field refernece
                // in the first method then we know the method pairs are cohesive, so return true
                //
                // We also want to check that the class field name was not found this check
                // this avoids marking a class field being used twice in a method as a sign of method
                // pair cohesion
                if (returnImmediate
                        && foundClassFieldReferences.contains(e.toString())
                        && !fieldsFoundThisCheck.contains(e.toString())) {
                    return true;
                }


                // mark the class filed as being found
                foundClassFieldReferences.add(e.toString());
                fieldsFoundThisCheck.add(e.toString());
            }
        }

        // getting to this point indicates we are either processing the class field references for the first method
        // OR we have finished looking for class field names in the second method and have found no one field used in common
        // in both methods, i.e. the method pair has no cohesion, so return false
        return false;
    }

    private boolean checkForMethodPair (String methodA, String methodB) {
        boolean ret = false;

        try {
            boolean present = classMethodPairs.get(methodA).get(methodB);
            ret = true;
        } catch (Exception e) {

        }

        try {
            boolean present = classMethodPairs.get(methodB).get(methodA);
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
