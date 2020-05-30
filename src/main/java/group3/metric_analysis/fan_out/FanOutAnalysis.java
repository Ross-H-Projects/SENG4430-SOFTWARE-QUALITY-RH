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

            // For each constructor and method definition in class, calculate the module and method mode scores
            // Add the method mode result to the hashmap and add the module mode score to the counter
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                methodFanOutScores.put(methodObject.getSignature(), calculateFanOutForMethod(methodObject));
                moduleModeCount += calculateModuleFanOutForMethod(classObject, methodObject);
            }
            for (CtConstructor<?> constructorObject : getConstructors(classObject)) {
                methodFanOutScores.put(constructorObject.getSignature(), calculateFanOutForConstructor(constructorObject));
                moduleModeCount += calculateModuleFanOutForConstructor(classObject, constructorObject);
            }

            // Add the method mode results hashmap to the method mode scores map under the key of the class name
            methodModeScores.put(classObject.getQualifiedName(), methodFanOutScores);
            // Add the module mode score under the key of the class name
            moduleModeScores.put(classObject.getQualifiedName(), moduleModeCount);

        }
    }

    private int calculateFanOutForMethod (CtMethod<?> method) {
        // Count all invocations
        List<CtInvocation<?>> methodCalls = method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
        return methodCalls.size();
    }

    private int calculateFanOutForConstructor (CtConstructor<?> constructor) {
        // Count all invocations
        List<CtInvocation<?>> methodCalls = constructor.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class));
        return methodCalls.size() - 1;
    }

    private int calculateModuleFanOutForMethod (CtClass<?> ctClass, CtMethod<?> method) {
        // Count only invocations that do not call local methods to class name
        Integer count = 0;
        for (CtInvocation<?> invocation : method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class))) {
            if (invocation.getExecutable().getDeclaringType() != null) {
                String className = invocation.getExecutable().getDeclaringType().toString();
                if (!className.equals(ctClass.getQualifiedName())) {
                    count += 1;
                }
            } else {
                count += 1;
            }
        }
        return count;
    }

    private int calculateModuleFanOutForConstructor (CtClass<?> ctClass, CtConstructor<?> constructor) {
        // Count only invocations that do not call local methods to class name
        Integer count = 0;
        for (CtInvocation<?> invocation : constructor.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class))) {
            String className = invocation.getExecutable().getDeclaringType().toString();
            if (!className.equals(ctClass.getQualifiedName())) {
                count += 1;
            }
        }
        return count - 1;
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
