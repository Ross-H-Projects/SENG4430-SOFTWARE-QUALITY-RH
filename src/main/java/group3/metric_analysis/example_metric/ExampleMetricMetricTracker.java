package group3.metric_analysis.example_metric;

import group3.MetricTracker;
import group3.metric_analysis.conditonal_nesting.metric_trackers.MethodTracker;

import java.util.HashMap;
import java.util.Map;

public class ClassTracker extends MetricTracker {
    HashMap<String, HashMap<String, Integer>> metricCountsMap = new HashMap<String, HashMap<String, Integer>>();

    public String getMetricProgramLevelJson() {
    }

    public String getMetricClassLevelJson() {
    }

    public String getMetricMethodLevelJson() {
        for (Map.Entry<String, HashMap<String, Integer>> entry : metricCountsMap.entrySet()) {
            String class_name = entry.getKey();
            Object class_methods = entry.getValue();
        }
    }

    public int getTotalProgramCount() {
    }
    public int getTotalClassCount(Map.Entry<String, Map<String, Integer>> classObject) {
        for (Map.Entry<String, Map<String, Integer>> entry : classObject.entrySet()) {
            String class_name = entry.getKey();
            Object class_methods = entry.getValue();
        }
    }

    public int getTotalMethodCount() {}

    public String toJson(MetricOutputLevel outputLevel) {
        String jsonString = "";
        if (outputLevel.equals(MetricOutputLevel.PROGRAM_LEVEL)) {
            jsonString = MetricOutputLevel.PROGRAM_LEVEL.name();
        } else if (outputLevel.equals(MetricOutputLevel.CLASS_LEVEL)) {
            jsonString = MetricOutputLevel.CLASS_LEVEL.name();
        } else if (outputLevel.equals(MetricOutputLevel.METHOD_LEVEL)) {
            jsonString = MetricOutputLevel.METHOD_LEVEL.name();
        }
        return jsonString;
    }
}



