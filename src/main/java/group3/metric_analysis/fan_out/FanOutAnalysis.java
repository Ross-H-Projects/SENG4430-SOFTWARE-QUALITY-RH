package group3.metric_analysis.fan_out;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.code.CtConstructorCall;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;


public class FanOutAnalysis extends MetricAnalysis {

    // Create hashmaps for fan out analysis. One stores results on a module level (score per class on how many invocations access externally defined methods).
    // The other stores a result per method and includes all invocations within each method
    private HashMap<String, HashMap<String, Integer>> methodModeScores;
    private HashMap<String, Integer> moduleModeScores;


    public FanOutAnalysis() {
        methodModeScores = new HashMap<String, HashMap<String, Integer>>();
        moduleModeScores = new HashMap<String, Integer>();
    }

    // Getter methods
    public HashMap<String, HashMap<String, Integer>> getMethodModeScores() {
        return methodModeScores;
    }

    public HashMap<String, Integer> getModuleModeScores() {
        return moduleModeScores;
    }


    // Main analysis method
    public void performAnalysis (Launcher launcher) {
        // Iterate over all classes in launcher
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            // Create intermediate hashmap for each method within the class
            HashMap<String, Integer> methodFanOutScores = new HashMap<String, Integer>();
            // Initialise the module score to zero
            Integer moduleModeCount = 0;
            ArrayList<String> uniqueCalls = new ArrayList<>();

            // For each constructor and method definition in class, calculate the module and method mode scores
            // Add the method mode result to the hashmap and add the module mode score to the counter
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                methodFanOutScores.put(methodObject.getSignature(), calculateMethodModeFanOut(methodObject));
                moduleModeCount += calculateModuleFanOut(classObject, methodObject, uniqueCalls);
            }
            for (CtConstructor<?> constructorObject : getConstructors(classObject)) {
                methodFanOutScores.put(constructorObject.getSignature(), calculateMethodModeFanOut(constructorObject) - 1);
                moduleModeCount += calculateModuleFanOut(classObject, constructorObject, uniqueCalls) - 1;
            }

            // Add the method mode results hashmap to the method mode scores map under the key of the class name
            methodModeScores.put(classObject.getQualifiedName(), methodFanOutScores);
            // Add the module mode score under the key of the class name
            moduleModeScores.put(classObject.getQualifiedName(), moduleModeCount);

        }
    }

    private int calculateMethodModeFanOut (CtElement caller) {
        // Count all invocations
        Integer count = 0;
        ArrayList<String> uniqueCalls = new ArrayList<>();
        for (CtInvocation invocation : caller.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class))) {
            if (invocation.getExecutable().getSignature() != null) {
                String methodName = invocation.getExecutable().getSignature();
                if (!uniqueCalls.contains(methodName)) {
                    count += 1;
                    uniqueCalls.add(methodName);
                }
            }
        }
        for (CtConstructorCall<?> call : caller.getElements(new TypeFilter<CtConstructorCall<?>>(CtConstructorCall.class))) {
            if (call.getExecutable().getDeclaringType() != null) {
                String methodName = call.getExecutable().getSignature();
                if (!uniqueCalls.contains(methodName)) {
                    count += 1;
                    uniqueCalls.add(methodName);
                }
            }
        }
        return count;
    }


    private int calculateModuleFanOut (CtClass<?> ctClass, CtElement caller, ArrayList<String> uniqueCalls) {
        // Count only invocations whos callee is not the caller and has not yet been called in caller (unique only)
        Integer count = 0;
        for (CtInvocation<?> invocation : caller.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class))) {
            if (invocation.getExecutable().getDeclaringType() != null) {

                String className = invocation.getExecutable().getDeclaringType().toString();
                if (!className.equals(ctClass.getQualifiedName()) && !uniqueCalls.contains(className)) {
                    count += 1;
                    uniqueCalls.add(className);
                }
            }
        }
        for (CtConstructorCall<?> call : caller.getElements(new TypeFilter<CtConstructorCall<?>>(CtConstructorCall.class))) {
            if (call.getExecutable().getDeclaringType() != null) {
                String className = call.getExecutable().getDeclaringType().toString();
                if (!className.equals(ctClass.getQualifiedName()) && !uniqueCalls.contains(className)) {
                    count += 1;
                    uniqueCalls.add(className);
                }
            }
        }
        return count;
    }

    private static ArrayList<CtMethod<?>> getMethods(CtClass<?> classObject) {
        // Helper method to return a list of methods within a class
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }

    private static ArrayList<CtConstructor<?>> getConstructors(CtClass<?> classObject) {
        // Helper method to return a list of constructors within a class
        Set<? extends CtConstructor<?>> constructorCollection = classObject.getConstructors();
        return new ArrayList<CtConstructor<?>>(constructorCollection);
    }
}
