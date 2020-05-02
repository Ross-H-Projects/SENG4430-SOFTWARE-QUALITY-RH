package group3.metric_analysis.comments_counts;

import group3.MetricTracker;
import group3.metric_analysis.conditonal_nesting.metric_trackers.MethodTracker;

import java.util.HashMap;
import java.util.Map;

public class CommentsCountMetricTracker extends MetricTracker {
    HashMap<String, HashMap<String, Integer>> metricCountsMap = new HashMap<String, HashMap<String, Integer>>();

    public String toJson(MetricOutputLevel outputLevel) {
        return "";
    }
}



