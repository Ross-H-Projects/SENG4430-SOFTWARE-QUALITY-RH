// based on the description of cohesion here:
// https://help.semmle.com/wiki/pages/viewpage.action?pageId=29394037


package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashMap;
import java.util.List;


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
        classMethodPairs = new HashMap<String, HashMap<String, Boolean>>();
    }

    public void performAnalysis (Launcher launcher) {
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));

        for (CtClass<?> c : classes) {
            ctClasses.put(c.getQualifiedName(), c);
            classCohesionScores.put(c.getQualifiedName(), calculateCohesionForClass(c));
        }

    }

    private int calculateCohesionForClass (CtClass<?> currentClass) {

        boolean methodPairExists;
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
                computeMethodPairCoherence(methodA, methodB);
            }
        }


        return 0;
    }

    private void computeMethodPairCoherence (CtMethod<?> methodA, CtMethod<?> methodB) {

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
