package group3.metric_analysis.length_of_identifiers;

import group3.MetricTracker;
import org.apache.commons.cli.*;
import spoon.Launcher;

/**
 * Launches and reads result of LengthofIdentifierAnalysis
 * @author DanielSands
 */
public class LengthOfIdentifiersTracker extends MetricTracker {
   private LengthOfIdentifiersAnalysis lengthOfIdentifiersAnalysis;

   //Default cutoff point for noteworthy identifiers
   private int noteworthyCutOffPoint = 4;

    public LengthOfIdentifiersTracker(String[] args) {
        Options options = new Options();
        //Add option for user to specify their own cutoff point
        options.addOption("cutoff", true, "Noteworthy identifiers cutoff value to display");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String cutOffArg = cmd.getOptionValue("cutoff");
        if (cutOffArg != null) {
            try {
                //If cutoff point is provided, update the cutoff variable
                noteworthyCutOffPoint = Integer.parseInt(cutOffArg);
            } catch (NumberFormatException e) {
                System.out.println("Length of identifier cutoff must be an integer value");
                System.exit(1);
            }
        }
        lengthOfIdentifiersAnalysis = new LengthOfIdentifiersAnalysis(noteworthyCutOffPoint);
    }

    /**
     * Runs the analysis
     * @param launcher
     */
    @Override
    public void run(Launcher launcher) {
        lengthOfIdentifiersAnalysis.performAnalysis(launcher);
    }

    /**
     * Produces Json output of metric analysis
     */
    @Override
    public String toJson() { //TODO: Make actual Json with the help of Gson
        return "Class average scores: " + lengthOfIdentifiersAnalysis.getClassLengthOfIdentifiersScores().toString()
                + " Noteworthy identifiers" + lengthOfIdentifiersAnalysis.getNoteworthyLengthOfIdentifierScores();
    }
}
