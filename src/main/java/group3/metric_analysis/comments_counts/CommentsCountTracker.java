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
    private boolean outputAll = false;
    private boolean totalCount = false;
    private boolean docStringCount = false;
    private boolean inlineCount = false;

    public CommentsCountTracker(String[] args) {
        Options options = new Options();
        options.addOption("output", true, "type of metric to run and output");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }


        String[] outputTypes = cmd.getOptionValues("output");
        for (String outputType : outputTypes) {
            switch (outputType) {
                case "*":
                    outputAll = true;
                    break;
                case "totalCount":
                    totalCount = true;
                    break;
                case "docStringCount":
                    docStringCount = true;
                    break;
                case "inlineCount":
                    inlineCount = true;
                    break;
            }
        }
        commentsCountAnalysis = new CommentsCountAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        commentsCountAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        String json = "";
        if (outputAll) {
            json+= "Total Comments Count: " + commentsCountAnalysis.getClassCommentsTotalCountScores().toString();
            json+= "\nDoc String Comments Count: " + commentsCountAnalysis.getClassCommentsDocStringCountScores().toString();
            json+= "\nInline Comments Count: " + commentsCountAnalysis.getClassCommentsInlineCountScores().toString();
        }
        if (totalCount) {
            json+= "Total Comments Count: " + commentsCountAnalysis.getClassCommentsTotalCountScores().toString();
        }
        if (docStringCount) {
            json+= "Doc String Comments Count: " + commentsCountAnalysis.getClassCommentsDocStringCountScores().toString();
        }
        if (inlineCount) {
            json+= "Inline Comments Count: " + commentsCountAnalysis.getClassCommentsInlineCountScores().toString();
        }
        return json;
     }
}



