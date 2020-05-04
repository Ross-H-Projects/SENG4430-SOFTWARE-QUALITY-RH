package group3.metric_analysis.comments_counts;

import group3.MetricTracker;
import group3.Metrics;
import group3.metric_analysis.fan_out.FanOutAnalysis;
import org.apache.commons.cli.*;
import spoon.Launcher;

import java.util.HashMap;
import java.util.Map;

public class CommentsCountTracker extends MetricTracker {

    private CommentsCountAnalysis commentsCountAnalysis;

    private boolean onAll = false;
    private boolean onClass = false;
    private boolean onMethod = false;

    public CommentsCountTracker(String[] args) {
        Options options = new Options();
        options.addOption("mode", true, "");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String metricMode = cmd.getOptionValue("mode");
        switch (metricMode) {
            case "*":
                onAll = true;
                break;
            case "onClass":
                onClass = true;
                break;
            case "onMethod":
                onMethod = true;
                break;
            default:
                System.out.println("-mode flag: invalid flag");
                System.exit(1);
                break;
        }
        commentsCountAnalysis = new CommentsCountAnalysis(onAll, onClass, onMethod);
    }

    @Override
    public void run(Launcher launcher) {
        commentsCountAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        String json = commentsCountAnalysis.toJson();
        return json;
     }
}



