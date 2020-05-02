package group3.metric_analysis.conditonal_nesting.metric_trackers;

import java.util.HashMap;

public class ClassTracker extends MetricTracker {
    private HashMap<String, MethodTracker> methodMap = new HashMap<String, MethodTracker>();
    public ClassTracker() {
    }
    public HashMap<String, MethodTracker> getMethodMap() {
        return methodMap;
    }
    public void addMethod(String methodName, MethodTracker methodTracker) {
        methodMap.put(methodName, methodTracker);
    }
    public MethodTracker getMethod(String methodName) {
        return methodMap.get(methodName);
    }
}



