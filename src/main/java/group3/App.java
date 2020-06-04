package group3;
import org.apache.commons.cli.*;
import spoon.Launcher;

import java.util.ArrayList;


public class App {

        // local launchers to be reused by metrics
        // launcher with comments included
        private static Launcher launcher;
        // launcher with comments removed
        private static Launcher launcherNoComments;

        // metrics class to store and operate on all metrics at once
        private static Metrics metrics;

        // outputs class to store and operate on all outputs at once
        private static Outputs outputs;

        public static void main(String[] args )
        {
                // process arguments to create launchers, metrics and outputs
                processArgs(args);
                // run analysis of each metric
                metrics.runMetrics(launcher, launcherNoComments);
                // get result JSON from metrics
                ArrayList<String> metricResults = metrics.getResults();
                // pass result to all outputs
                outputs.create(metricResults);
        }

        public static void processArgs(String[] args) {
                // check for required arguments
                if (args.length < 2) {
                        System.out.println("Error: Invalid Arguments");
                        System.out.println("Correct Arguments: <SourceFileOrDirectory>  [-m \"<metric> [<metric-flag ...] ...\"] [-o \"<output> [<output-flag> ...] ...\"");
                        System.exit(1);
                }

                // prepare command line options
                Options options = new Options();

                options.addOption("m", true, "metric and definitions");
                options.addOption("o", true, "output and definitions");

                // parse options
                CommandLineParser parser = new DefaultParser();
                CommandLine cmd = null;
                try {
                        cmd = parser.parse(options, args);
                } catch (ParseException e) {
                        e.printStackTrace();
                        System.exit(1);
                }

                // create all metrics defined by -m
                metrics = new Metrics(cmd.getOptionValues("m"));

                // If no output options given, default to cmd
                String[] outputOptions = cmd.getOptionValues("o");
                if (outputOptions == null) outputOptions = new String[] {"cmd"};
                outputs = new Outputs(outputOptions);

                // create both launchers on input path
                launcher = Utilities.importCodeSample(args[0], true);
                launcherNoComments = Utilities.importCodeSample(args[0], false);
        }
}
