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
    private HashMap<String, HashMap<String, Integer>> methodModeFanInScores;
    private HashMap<String, Integer> moduleModeFanInScores;

    public FanInAnalysis() {
        methodModeFanInScores = new HashMap<>();
        moduleModeFanInScores = new HashMap<>();
    }

    public HashMap<String, HashMap<String, Integer>> getMethodModeFanInScores() {
        return methodModeFanInScores;
    }

    public HashMap<String, Integer> getModuleModeFanInScores() {
        return moduleModeFanInScores;
    }

    public void performAnalysis (Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, Integer> methodFanInScores = new HashMap<String, Integer>();
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                methodFanInScores.put(methodObject.getSignature(), 0);
            }
//            for (CtConstructor<?> constructorObject : getConstructors(classObject)) {
//                methodFanInScores.put(constructorObject.getSignature(), 0);
//            }
            methodModeFanInScores.put(classObject.getQualifiedName(), methodFanInScores);
            moduleModeFanInScores.put(classObject.getQualifiedName(), 0);
        }

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
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }

    private static ArrayList<CtConstructor<?>> getConstructors(CtClass<?> classObject) {
        Set<? extends CtConstructor<?>> constructorCollection = classObject.getConstructors();
        return new ArrayList<CtConstructor<?>>(constructorCollection);
    }
}


