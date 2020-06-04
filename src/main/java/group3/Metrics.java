package group3;

import group3.metric_analysis.comments_counts.CommentsCountTracker;
import group3.metric_analysis.conditional_nesting.ConditionalNestingTracker;
import group3.metric_analysis.depth_inheritance_tree.DepthInheritanceTreeTracker;
import group3.metric_analysis.fog_index.FogIndexTracker;
import group3.metric_analysis.fan_in.FanInTracker;
import group3.metric_analysis.halstead_complexity.HalsteadComplexityTracker;
import group3.metric_analysis.lack_of_cohesion.LackOfCohesionTracker;
import group3.metric_analysis.fan_out.FanOutTracker;
import group3.metric_analysis.coupling.CouplingTracker;
import group3.metric_analysis.length_of_identifiers.LengthOfIdentifiersTracker;
import group3.metric_analysis.cyclomatic_complexity.CyclomaticTracker;
import group3.metric_analysis.method_count.MethodCountTracker;
import spoon.Launcher;

import java.util.*;

public class Metrics {
    // list of all metric trackers
    private ArrayList<MetricTracker> metricTrackers;

    // create the corresponding metric tracker for each metric passed in
    // send the metric arguments to the constructor
    public Metrics (String[] metricDefinitions) {
        metricTrackers = new ArrayList<>();
        for (String def : metricDefinitions) {

            String arr[] = def.split(" ");
            MetricTracker tracker;
            switch (arr[0]) {
                case "inheritance_depth":
                    tracker = new DepthInheritanceTreeTracker(Arrays.copyOfRange(arr, 1, arr.length));
                    break;
                case "cohesion_score":
                    tracker = new LackOfCohesionTracker(Arrays.copyOfRange(arr, 1, arr.length));
                    break;
                case "depth_conditional_nesting":
                    tracker = new ConditionalNestingTracker(Arrays.copyOfRange(arr, 1, arr.length));
                    break;
                case "fan_in":
                    tracker = new FanInTracker(Arrays.copyOfRange(arr, 1, arr.length));
                    break;
                case "fan_out":
                    tracker = new FanOutTracker(Arrays.copyOfRange(arr, 1, arr.length));
                    break;
                case "comments_count":
                    tracker = new CommentsCountTracker(Arrays.copyOfRange(arr, 1, arr.length));
                    break;
                case "coupling":
                    tracker = new CouplingTracker(Arrays.copyOfRange(arr, 1, arr.length));
                    break;
                case "length_of_identifiers":
                    tracker = new LengthOfIdentifiersTracker(Arrays.copyOfRange(arr, 1, arr.length));
                    break;
                case "halstead_complexity":
                    tracker = new HalsteadComplexityTracker(Arrays.copyOfRange(arr, 1, arr.length));
                    break;
                case "fog_index":
                    tracker = new FogIndexTracker(Arrays.copyOfRange(arr, 1, arr.length));
                    break;
                case "cyclomatic_complexity":
                    tracker = new CyclomaticTracker(Arrays.copyOfRange(arr,1,arr.length));
                    break;
                case "method_count":
                    tracker = new MethodCountTracker(Arrays.copyOfRange(arr,1,arr.length));
                    break;
                default:
                    throw new IllegalArgumentException("Metric " + arr[0] + " is invalid");

            }
            metricTrackers.add(tracker);

        }
    }

    // run each metric on the desired launcher
    public void runMetrics(Launcher launcher, Launcher launcherNoComments) {
        for (MetricTracker tracker : metricTrackers) {
            if (!tracker.includeComments()) {
                tracker.run(launcherNoComments);
            } else {
                tracker.run(launcher);
            }

        }
    }

    // Get the result string and order based on metric
    public ArrayList<String> getResults() {
        ArrayList<String> results = new ArrayList<String>();

        // sort the metric trackers, this is done so that system testing
        // has a guaranteed order of the metrics output
        Collections.sort(metricTrackers, new Comparator<MetricTracker>() {
            @Override
            public int compare(MetricTracker m1, MetricTracker m2) {
                String m1Class = m1.getClass().toString();
                String m2Class = m2.getClass().toString();

                return m1.getClass().toString().compareTo(m2.getClass().toString());
            }
        });

        for (MetricTracker tracker : metricTrackers) {
            results.add(tracker.toJson());
        }
        return results;
    }

    // Getter for metric trackers
    public List<MetricTracker> getMetricTrackers() {
        return metricTrackers;
    }
}
