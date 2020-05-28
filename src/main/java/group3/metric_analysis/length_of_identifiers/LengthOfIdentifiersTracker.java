package group3.metric_analysis.length_of_identifiers;

import group3.MetricTracker;
import spoon.Launcher;

/**
 * TODO: documentation
 * @author DanielSands
 */
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
    public String toJson() { //TODO: Maybe have this in toJson method in LoIAnalysis and then just call the function here, like in CommentsCount
        return "Class average scores: " + lengthOfIdentifiersAnalysis.getClassLengthOfIdentifiersScores().toString()
                + " Noteworthy identifiers" + lengthOfIdentifiersAnalysis.getNoteworthyLengthOfIdentifierScores(); //TODO: Decide how to go about showing notwortyLengthOfIdentifiers
    }
}
