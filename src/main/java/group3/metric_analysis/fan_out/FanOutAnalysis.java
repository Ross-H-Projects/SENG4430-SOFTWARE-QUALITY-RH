package group3.metric_analysis.fan_out;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;


public class FanOutAnalysis extends MetricAnalysis {
    private HashMap<String, HashMap<String, Integer>> methodModeScores;
    private HashMap<String, Integer> moduleModeScores;

//    private Boolean moduleMode;

    public FanOutAnalysis() {
        methodModeScores = new HashMap<String, HashMap<String, Integer>>();
        moduleModeScores = new HashMap<String, Integer>();
//        this.moduleMode = moduleMode;
    }

    public HashMap<String, HashMap<String, Integer>> getMethodModeScores() {
        return methodModeScores;
    }

    public HashMap<String, Integer> getModuleModeScores() {
        return moduleModeScores;
    }

    public void performAnalysis (Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, Integer> methodFanOutScores = new HashMap<String, Integer>();
            Integer moduleModeCount = 0;
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                methodFanOutScores.put(methodObject.getSignature(), calculateFanOutForMethod(methodObject));
                moduleModeCount += calculateModuleFanOutForMethod(classObject, methodObject);
            }
            for (CtConstructor<?> constructorObject : getConstructors(classObject)) {
                methodFanOutScores.put(constructorObject.getSignature(), calculateFanOutForConstructor(constructorObject));
                moduleModeCount += calculateModuleFanOutForConstructor(classObject, constructorObject);
            }
            methodModeScores.put(classObject.getQualifiedName(), methodFanOutScores);
            moduleModeScores.put(classObject.getQualifiedName(), moduleModeCount);

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

    private int calculateModuleFanOutForMethod (CtClass<?> ctClass, CtMethod<?> method) {
        Integer count = 0;
        for (CtInvocation<?> invocation : method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class))) {
            String className = invocation.getExecutable().getDeclaringType().toString();
            if (!className.equals(ctClass.getQualifiedName())) {
                count += 1;
            }
        }
        return count;
    }

    private int calculateModuleFanOutForConstructor (CtClass<?> ctClass, CtConstructor<?> constructor) {
        Integer count = 0;
        for (CtInvocation<?> invocation : constructor.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class))) {
            String className = invocation.getExecutable().getDeclaringType().toString();
            if (!className.equals(ctClass.getQualifiedName())) {
                count += 1;
            }
        }
        System.out.println(count);
        return count - 1;
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
