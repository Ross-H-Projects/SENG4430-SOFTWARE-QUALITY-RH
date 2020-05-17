package group3.metric_analysis.fog_index;

import group3.MetricTracker;
import spoon.Launcher;

public class FogIndexTracker extends MetricTracker {
    private FogIndexAnalysis fogIndexAnalysis;

    public FogIndexTracker(String[] args){
        fogIndexAnalysis = new FogIndexAnalysis();
    }
    @Override
    public void run(Launcher launcher) {
        fogIndexAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        return fogIndexAnalysis.getReturn();
    }
}
