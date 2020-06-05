package group3.metric_analysis.method_count;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.code.*;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MethodCountAnalysis extends MetricAnalysis {

    private HashMap<String, Integer> classMethodScores;

    //Constructor
    public MethodCountAnalysis() {
        classMethodScores = new HashMap<String, Integer>();
    }

    //Returns analysis
    public HashMap<String, Integer> getClassMethodScores() {
        return classMethodScores;
    }

    //Performs analysis, counting the number of methods per class
    public void performAnalysis (Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            List<CtMethod> methods = classObject.getElements(new TypeFilter<CtMethod>(CtMethod.class));
            classMethodScores.put(classObject.getQualifiedName(), methods.size());
        }
    }
}