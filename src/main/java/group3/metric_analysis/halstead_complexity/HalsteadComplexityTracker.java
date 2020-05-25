package group3.metric_analysis.halstead_complexity;

import group3.MetricTracker;
import spoon.Launcher;

public class HalsteadComplexityTracker extends MetricTracker {
    private HalsteadComplexityAnalysis halsteadComplexityAnalysis;

    public HalsteadComplexityTracker(String[] args) {
        super.doIncludeComments = false;
        halsteadComplexityAnalysis = new HalsteadComplexityAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        halsteadComplexityAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        return String.valueOf(halsteadComplexityAnalysis.getComplexityMeasures());
    }
}


