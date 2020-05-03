package group3.metric_analysis.conditonal_nesting.metric_trackers;

import java.util.HashMap;

public class ProgramTracker extends MetricTracker{
    private HashMap<String, ClassTracker> classMap = new HashMap<String, ClassTracker>();

    public ProgramTracker() {
    }

    public HashMap<String, ClassTracker> getClassMap() {
        return classMap;
    }
    public void addClass(String className, ClassTracker classTracker) {
        classMap.put(className, classTracker);
    }
    public ClassTracker getClass(String className) {
        return classMap.get(className);
    }
}



