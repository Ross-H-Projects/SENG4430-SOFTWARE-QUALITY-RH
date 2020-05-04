package group3.metric_analysis.depth_inheritance_tree;


import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class DepthInheritanceTreeAnalysis extends MetricAnalysis {
    private HashMap<String, Integer> visited_classes;
    private HashMap<String, CtClass<?>> ctClasses;

    private int maxDepth;


    public DepthInheritanceTreeAnalysis() {
        visited_classes = new HashMap<String, Integer>();
        ctClasses = new HashMap<String, CtClass<?>>();
        maxDepth = 0;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void performAnalysis (Launcher launcher) {
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));

        for (CtClass<?> c : classes) {
            ctClasses.put(c.getQualifiedName(), c);
        }

        int currentDepth;
        for (CtClass<?> c : classes) {
            if (!visited_classes.containsKey(c.getQualifiedName())) {
                currentDepth = depthInheritanceRecursive(c);

                maxDepth= (currentDepth > maxDepth) ? currentDepth : maxDepth;
            }
        }
    }

    private int depthInheritanceRecursive(CtClass<?> currentClass) {

        if (!visited_classes.containsKey(currentClass.getQualifiedName())) { // current class has not had depth calculated yet,
                                                                             // add it, and go on to calculate depth
            visited_classes.put(currentClass.getQualifiedName(), 0);
        } else { // current class already had depth calculated, just return it
            return visited_classes.get(currentClass.getQualifiedName());
        }

        // calculate depth

        // curent class has a super class, so the depth is 1 + depth of the super class
        if (currentClass.getSuperclass() != null) {
            CtClass<?> superClass = ctClasses.get(currentClass.getSuperclass().getQualifiedName());

            int thisClassDepth = 1 + depthInheritanceRecursive(superClass);
            visited_classes.put(currentClass.getQualifiedName(), thisClassDepth);
            return thisClassDepth;
        }

        // current class has no super class, thus depth is 0
        return 0;
    }


}
