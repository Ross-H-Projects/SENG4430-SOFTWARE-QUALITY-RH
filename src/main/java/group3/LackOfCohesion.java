// based on the description of cohesion here:
// https://help.semmle.com/wiki/pages/viewpage.action?pageId=29394037
// try https://archive.is/Hc0TG if the above link does not work.


package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashMap;
import java.util.List;


public class LackOfCohesion extends MetricAnalysis {
    private int n1; // # of pairs of distinct methods in a class that DO NOT have at-least one commonly-accessed fields
    private int n2; // # of pairs of distinct methods in a class that DO have at-least one commonly accessed fields

    private HashMap<String, CtClass<?>> ctClasses;
    private HashMap<String, Integer> classCohesionScores;

    public LackOfCohesion () {
        n1 = 0;
        n2 = 0;
        ctClasses = new HashMap<String, CtClass<?>>();
        classCohesionScores = new HashMap<String, Integer>();
    }

    public MetricReturn performAnalysis (String fileName) {
        Launcher launcher = Utilities.importCodeSample(fileName);
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));

        for (CtClass<?> c : classes) {
            ctClasses.put(c.getQualifiedName(), c);
            classCohesionScores.put(c.getQualifiedName(), calculateCohesionForClass(c));
        }

        LackOfCohesionReturn result = new LackOfCohesionReturn();
        return result;
    }

    private int calculateCohesionForClass (CtClass<?> currentClass) {
        System.out.println(currentClass);
        return 0;
    }
}
