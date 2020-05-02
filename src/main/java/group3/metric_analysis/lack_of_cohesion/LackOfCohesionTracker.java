package group3.metric_analysis.lack_of_cohesion;

import group3.LackOfCohesion;
import group3.MetricTracker;
import spoon.Launcher;

public class LackOfCohesionTracker extends MetricTracker {
    private LackOfCohesion lackOfCohesion;

    public LackOfCohesionTracker(String[] args) {
        super.doIncludeComments = false;
        lackOfCohesion = new LackOfCohesion();
    }

    @Override
    public void run(Launcher launcher) {
        lackOfCohesion.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        return "";
    }
}
