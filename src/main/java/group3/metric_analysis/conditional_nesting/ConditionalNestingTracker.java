package group3.metric_analysis.conditional_nesting;

import group3.MetricTracker;
import spoon.Launcher;

public class ConditionalNestingTracker extends MetricTracker {

    private ConditionalNestingAnalysis conditionalNestingAnalysis;

    public ConditionalNestingTracker(String[] args) {
        conditionalNestingAnalysis = new ConditionalNestingAnalysis();
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
