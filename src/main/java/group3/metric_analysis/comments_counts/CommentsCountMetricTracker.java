package group3.metric_analysis.comments_counts;

import group3.MetricTracker;
import group3.metric_analysis.conditonal_nesting.metric_trackers.MethodTracker;
import spoon.Launcher;

import java.util.HashMap;
import java.util.Map;

public class CommentsCountMetricTracker extends MetricTracker {
    HashMap<String, HashMap<String, Integer>> metricCountsMap = new HashMap<String, HashMap<String, Integer>>();

    @Override
    public void run(Launcher launcher) {

    }

    public String toJson() {
        return "";
    }
}



