package group3.metric_analysis.fan_in;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtConstructor;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;


public class FanInAnalysis extends MetricAnalysis {

    // Create hashmaps for fan in analysis. One stores results on a module level (score per class on how many invocations access this class from an external source).
    // The other stores a result per method and includes all invocations of that method
    private HashMap<String, HashMap<String, Integer>> methodModeFanInScores;
    private HashMap<String, Integer> moduleModeFanInScores;

    public FanInAnalysis() {
        methodModeFanInScores = new HashMap<>();
        moduleModeFanInScores = new HashMap<>();
    }

    // Getter methods
    public HashMap<String, HashMap<String, Integer>> getMethodModeFanInScores() {
        return methodModeFanInScores;
    }

    public HashMap<String, Integer> getModuleModeFanInScores() {
        return moduleModeFanInScores;
    }

    // Main analysis method
    public void performAnalysis (Launcher launcher) {
        // Iterate over all classes and methods in provided launcher. Set default values in score hashmaps of 0. This enables the analysis to produce results only for code defined in the given launcher
        // rather than providing a result for the parent of every invocations. E.g. we don't want a fan in score for the number of System.out.println() calls
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, Integer> methodFanInScores = new HashMap<String, Integer>();
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                methodFanInScores.put(methodObject.getSignature(), 0);
            }
            methodModeFanInScores.put(classObject.getQualifiedName(), methodFanInScores);
            moduleModeFanInScores.put(classObject.getQualifiedName(), 0);
        }

        // Iterate over all classes, methods and constructors again, but this time inspect invocations and increase the callee's score
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                updateInnerInvocations(methodObject);
                updateModuleModeInvocations(classObject, methodObject);
            }
            for (CtConstructor<?> constructorObject : getConstructors(classObject)) {
                updateInnerInvocations(constructorObject);
                updateModuleModeInvocations(classObject, constructorObject);
            }
        }
    }

    private void updateInnerInvocations(CtElement method){
        // For every invocation, if the callee is within the inspected codebase, add 1 to its score
        for (CtInvocation<?> invocation : method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class))) {
            if (invocation.getExecutable().getDeclaration() != null) {
                String className = invocation.getExecutable().getDeclaringType().toString();
                String methodName = invocation.getExecutable().getSignature();

                if (methodModeFanInScores.containsKey(className) && methodModeFanInScores.get(className).containsKey(methodName)) {
                    HashMap<String, Integer> classHash = methodModeFanInScores.get(className);
                    classHash.put(methodName, classHash.get(methodName) + 1);
                    methodModeFanInScores.put(className, classHash);
                }
            }
        }
    }

    private void updateModuleModeInvocations(CtClass ctClass, CtElement method) {
        // For every invocation, if the callee is within the inspected codebase, but not the same the caller class, add 1 to the callee's score
        for (CtInvocation<?> invocation : method.getElements(new TypeFilter<CtInvocation<?>>(CtInvocation.class))) {
            if (invocation.getExecutable().getDeclaration() != null) {
                String className = invocation.getExecutable().getDeclaringType().toString();
                if (moduleModeFanInScores.containsKey(className) && !ctClass.getQualifiedName().equals(className)) {
                    moduleModeFanInScores.put(className, moduleModeFanInScores.get(className) + 1);
                }
            }
        }
    }


    private static ArrayList<CtMethod<?>> getMethods(CtClass<?> classObject) {
        // Helper method to get list of methods
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }

    private static ArrayList<CtConstructor<?>> getConstructors(CtClass<?> classObject) {
        // Helper method to get list of constructors
        Set<? extends CtConstructor<?>> constructorCollection = classObject.getConstructors();
        return new ArrayList<CtConstructor<?>>(constructorCollection);
    }
}


