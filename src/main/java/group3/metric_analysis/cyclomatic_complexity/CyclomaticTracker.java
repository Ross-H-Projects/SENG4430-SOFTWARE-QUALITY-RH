package group3.metric_analysis.cyclomatic_complexity;

import group3.MetricTracker;
import spoon.Launcher;

public class CyclomaticTracker extends MetricTracker {

    private CyclomaticAnalysis cyclomaticAnalysis;

    public CyclomaticTracker(String[] args) {
        cyclomaticAnalysis = new CyclomaticAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        cyclomaticAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        return cyclomaticAnalysis.getClassCMCScores().toString();
    }
}
