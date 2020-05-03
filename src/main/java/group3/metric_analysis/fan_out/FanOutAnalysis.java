package group3.metric_analysis.fan_out;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.InvocationFilter;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;


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
                methodFanOutScores.put(methodObject.getSignature(), calculateFanOutForMethod(methodObject));
                System.out.println(methodObject.getPath());
            }
            for (CtConstructor<?> constructorObject : getConstructors(classObject)) {
                methodFanOutScores.put(constructorObject.getSignature(), calculateFanOutForConstructor(constructorObject));
            }
            classFanOutScores.put(classObject.getQualifiedName(), methodFanOutScores);
        }
    }

    private int calculateFanOutForMethod (CtMethod<?> method) {
        List<CtInvocation<?>> methodCalls = method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
        return methodCalls.size();
    }

    private int calculateFanOutForConstructor (CtConstructor<?> constructor) {
        List<CtInvocation<?>> methodCalls = constructor.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
        return methodCalls.size() - 1;
    }

    private static ArrayList<CtMethod<?>> getMethods(CtClass<?> classObject) {
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }

    private static ArrayList<CtConstructor<?>> getConstructors(CtClass<?> classObject) {
        Set<? extends CtConstructor<?>> constructorCollection = classObject.getConstructors();
        return new ArrayList<CtConstructor<?>>(constructorCollection);
    }
}
