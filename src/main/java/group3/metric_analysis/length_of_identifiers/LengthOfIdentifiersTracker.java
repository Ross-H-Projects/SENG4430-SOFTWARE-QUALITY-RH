package group3.metric_analysis.length_of_identifiers;

import group3.MetricTracker;
import group3.metric_analysis.fan_out.FanOutAnalysis;
import spoon.Launcher;

public class LengthOfIdentifiersTracker extends MetricTracker {
   private LengthOfIdentifiersAnalysis lengthOfIdentifiersAnalysis;

    public LengthOfIdentifiersTracker(String[] args) {
        lengthOfIdentifiersAnalysis = new LengthOfIdentifiersAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        lengthOfIdentifiersAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        return "Class average scores: " + lengthOfIdentifiersAnalysis.getClassLengthOfIdentifiersScores().toString()
                + " Noteworthy identifiers" + lengthOfIdentifiersAnalysis.getNoteworthyLengthOfIdentifierScores(); //TODO: Decide how to go about showing notwortyLengthOfIdentifiers
    }
}
