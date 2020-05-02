package group3;

import group3.metric_analysis.comments_counts.CommentsCountTracker;
import group3.metric_analysis.depth_inheritance_tree.DepthInheritanceTreeTracker;
import group3.metric_analysis.fan_out.FanOutTracker;
import spoon.Launcher;

import java.util.ArrayList;
import java.util.List;

public class Metrics {
    private ArrayList<MetricTracker> metricTrackers;

    public Metrics (String[] metricDefinitions) {
        metricTrackers = new ArrayList<>();
        for (String def : metricDefinitions) {
            String arr[] = def.split(" ", 2);
            MetricTracker tracker;
            switch (arr[0]) {
                case "inheritance_depth":
                    tracker = new DepthInheritanceTreeTracker(arr[1]);
                    break;
//                case "cohesion_score":
////                    tracker = new CohesionScoreTracker(arr[1]);
//                    break;
//                case "depth_conditional_nesting":
////                    tracker = new DepthConditionalNestingTracker(arr[1]);
//                    break;
                case "fan_out":
                    tracker = new FanOutTracker(arr[1]);
                    break;
                case "comments_count":
                    tracker = new CommentsCountTracker(arr[1]);
                    break;
                default:
                    throw new IllegalArgumentException("Metric " + arr[0] + " is invalid");

            }
            metricTrackers.add(tracker);

        }
    }

    public void runMetrics(Launcher launcher) {
        for (MetricTracker tracker : metricTrackers) {
            tracker.run(launcher);
        }
    }

    public List<MetricTracker> getMetricTrackers() {
        return metricTrackers;
    }
}
