package group3.metric_analysis.conditonal_nesting;

import group3.MetricAnalysis;
import group3.Utilities;
import group3.metric_analysis.conditonal_nesting.metric_trackers.ClassTracker;
import group3.metric_analysis.conditonal_nesting.metric_trackers.MethodTracker;
import group3.metric_analysis.conditonal_nesting.metric_trackers.ProgramTracker;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

public class DepthOfConditionalNestingAnalysis extends MetricAnalysis {
    private ProgramTracker programTracker;

    public DepthOfConditionalNestingAnalysis() {
    }

    public int performAnalysis (String fileName) {
        System.out.println("accessing performAnalysis in DepthOfConditionalNestingAnalysis..");

        Launcher launcher = Utilities.importCodeSample(fileName);
        SourceCodeIteration codeIterator = new SourceCodeIteration();
        codeIterator.setLauncher(launcher);
        System.out.println("Forced param");
        codeIterator.setRootClass("ConditionalTest");

        for(CtType currentClass: codeIterator.getClasses()) {
            System.out.println(currentClass.getSimpleName());
            for(CtMethod method: codeIterator.getClassMethods(currentClass)) {
                System.out.println(method.getSimpleName());
                System.out.println(method.getBody().prettyprint());
            }
        }
        return 0;
    }

    public void setProgramTracker() {
        programTracker = new ProgramTracker();
    }
    public void setClassTracker(String className) {
        ClassTracker classTracker = new ClassTracker();
        programTracker.addClass(className, classTracker);
    }
    public void setMethodTracker(String className, String methodName) {
        ClassTracker classTracker = programTracker.getClass(className);
        MethodTracker methodTracker = new MethodTracker();
        classTracker.addMethod(methodName, methodTracker);
    }
    public ProgramTracker getProgramTracker() {
        return programTracker;
    }
    public ClassTracker getClassTracker(String className) {
        return programTracker.getClass(className);
    }
    public MethodTracker getMethodTracker(String className, String methodName) {
        return programTracker.getClass(className).getMethod(methodName);
    }
}
