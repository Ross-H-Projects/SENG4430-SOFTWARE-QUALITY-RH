package group3;
import org.apache.commons.cli.*;
//import group3.metric_analysis.conditonal_nesting.DepthOfConditionalNestingAnalysis;
import spoon.Launcher;

import java.util.ArrayList;


public class App {

        private static Launcher launcher;
        private static Launcher launcherNoComments;
        private static Metrics metrics;
        private static Outputs outputs;

        public static void main(String[] args )
        {
                processArgs(args);
                metrics.runMetrics(launcher, launcherNoComments);
                ArrayList<String> metricResults = metrics.getResults();
                outputs.create(metricResults);
        }

        public static void processArgs(String[] args) {
                if (args.length < 2) {
                        System.out.println("Error: Invalid Arguments");
                        System.out.println("Correct Arguments: <SourceFileOrDirectory>  [-m \"metric [metric flags]\"]");
                        System.exit(1);
                }

                Options options = new Options();

                options.addOption("m", true, "metric and definitions");
                options.addOption("o", true, "output and definitions");

                CommandLineParser parser = new DefaultParser();
                CommandLine cmd = null;
                try {
                        cmd = parser.parse(options, args);
                } catch (ParseException e) {
                        e.printStackTrace();
                        System.exit(1);
                }

                metrics = new Metrics(cmd.getOptionValues("m"));

                // If no output options given, default to cmd
                String[] outputOptions = cmd.getOptionValues("o");
                if (outputOptions == null) outputOptions = new String[] {"cmd"};
                outputs = new Outputs(outputOptions);

                launcher = Utilities.importCodeSample(args[0], true);
                launcherNoComments = Utilities.importCodeSample(args[0], false);
        }
}
