package group3;

import spoon.Launcher;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.InvocationFilter;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


public class FanOutAnalysis extends MetricAnalysis {
    private HashMap<String, HashMap<String, Integer>> classFanOutScores;

    public FanOutAnalysis() {
        classFanOutScores = new HashMap<String, HashMap<String, Integer>>();
    }

    public HashMap<String, HashMap<String, Integer>> getClassFanOutScores() {
        return classFanOutScores;
    }

    public void performAnalysis (Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, Integer> methodFanOutScores = new HashMap<String, Integer>();
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                methodFanOutScores.put(methodObject.getSimpleName(), calculateFanOutForMethod(methodObject));
            }
            classFanOutScores.put(classObject.getQualifiedName(), methodFanOutScores);
        }

        System.out.println(classFanOutScores);

    }

    private int calculateFanOutForMethod (CtMethod<?> method) {
        List<CtInvocation<?>> methodCalls = method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
        return methodCalls.size();
    }

    private static ArrayList<CtMethod<?>> getMethods(CtClass<?> classObject){
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }
}
