package group3.metric_analysis.fan_out;

import group3.MetricTracker;
import spoon.Launcher;

public class FanOutTracker extends MetricTracker {

    private FanOutAnalysis fanOutAnalysis;

    public FanOutTracker(String args) {
        fanOutAnalysis = new FanOutAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        fanOutAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        return fanOutAnalysis.getClassFanOutScores().toString();
    }
}
