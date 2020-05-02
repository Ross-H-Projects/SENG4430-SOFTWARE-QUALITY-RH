package group3.metric_analysis;

import group3.*;
import group3.metric_analysis.conditonal_nesting.DepthOfConditionalNestingAnalysis;
import spoon.Launcher;

import java.util.HashMap;
import java.util.List;

public class Metrics {
    private List<MetricTracker> metricTrackers;

    public Metrics (String[] metricDefinitions) {
        for (String def : metricDefinitions) {
            String arr[] = def.split(" ", 2);
            MetricTracker tracker;
            switch (arr[0]) {
                case "inheritance_depth":
//                    tracker = new depthInheritanceTracker(arr[1]);
                    break;
                case "cohesion_score":
//                    tracker = new cohesionScoreTracker(arr[1]);
                    break;
                case "depth_conditional_nesting":
//                    tracker = new depthConditionalNestingTracker(arr[1]);
                    break;
                case "fan_out":
                    System.out.println("FANOUT");
//                    tracker = new fanOutTracker(arr[1]);
                    break;
                default:
                    throw new IllegalArgumentException("Metric " + arr[0] + " is invalid");

            }
//            metricTrackers.add(tracker);
        }
    }

    public void runMetrics(Launcher launcher) {
        for (MetricTracker tracker : metricTrackers) {
//            tracker.run(launcher);
        }
    }

    public List<MetricTracker> getMetricTrackers() {
        return metricTrackers;
    }
}
