package group3.metric_analysis.method_count;

import group3.MetricTracker;
import spoon.Launcher;

public class MethodCountTracker extends MetricTracker {

    private MethodCountAnalysis methodAnalysis;

    public MethodCountTracker(String[] args) {
        methodAnalysis = new MethodCountAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        methodAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        return methodAnalysis.getClassMethodScores().toString();
    }
}
