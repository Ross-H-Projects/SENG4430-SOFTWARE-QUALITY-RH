package group3.metric_analysis.conditonal_nesting;

import group3.MetricAnalysis;
import group3.metric_analysis.conditonal_nesting.metric_trackers.ClassTracker;
import group3.metric_analysis.conditonal_nesting.metric_trackers.MethodTracker;
import group3.metric_analysis.conditonal_nesting.metric_trackers.ProgramTracker;

import java.util.HashMap;

public class DepthOfConditionalNestingAnalysis extends MetricAnalysis {
    private ProgramTracker programTracker;

    public DepthOfConditionalNestingAnalysis() {
    }

    public int performAnalysis (String fileName) {
//        System.out.println("accessing performAnalysis in DepthOfConditionalNestingAnalysis..");
//
//        Launcher launcher = Utilities.importCodeSample(fileName);
//
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
