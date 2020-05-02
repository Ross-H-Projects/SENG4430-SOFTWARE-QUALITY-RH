package group3;

import spoon.Launcher;

import java.util.HashMap;

public class FanOutTracker extends MetricTracker {

    private FanOutAnalysis fanOutAnalysis;

    public FanOutTracker(String args) {
        System.out.println(args);
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
