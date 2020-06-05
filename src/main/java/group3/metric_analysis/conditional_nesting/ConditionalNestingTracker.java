package group3.metric_analysis.conditional_nesting;

import group3.MetricTracker;
import group3.metric_analysis.comments_counts.CommentsCountAnalysis;
import org.apache.commons.cli.*;
import spoon.Launcher;

public class ConditionalNestingTracker extends MetricTracker {

    private ConditionalNestingAnalysis conditionalNestingAnalysis;

    public ConditionalNestingTracker(String[] args) {
        Options options = new Options();
        options.addOption("depth", true, "");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String minDepthString = cmd.getOptionValue("depth");
        int minDepth = 3;
        if (minDepthString != null) {
            try
            {
                minDepth = Integer.parseInt(minDepthString);
            }
            catch (NumberFormatException e)
            {
                System.out.println("-depth flag value is not a integer");
                System.exit(1);
            }
        }
        conditionalNestingAnalysis = new ConditionalNestingAnalysis(minDepth);
    }

    @Override
    public void run(Launcher launcher) {
        conditionalNestingAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        return conditionalNestingAnalysis.getClassConditionalNestingScoresJson();
    }
}
