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

    public CommentsCountTracker(String[] args) {
        commentsCountAnalysis = new CommentsCountAnalysis();

        Options options = new Options();
        options.addOption("output", false, "type of metric to run and output");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        metrics = new Metrics(cmd.getOptionValues("m"));
    }

    @Override
    public void run(Launcher launcher) {
        commentsCountAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {return commentsCountAnalysis.getClassCommentsCountScoresScores().toString();}
}



