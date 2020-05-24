package group3.metric_analysis.fog_index;

import group3.MetricTracker;
import spoon.Launcher;

/**
 * @author DanielSands
 */
public class FogIndexTracker extends MetricTracker {
    private FogIndexAnalysis fogIndexAnalysis;

    public FogIndexTracker(String[] args){
        fogIndexAnalysis = new FogIndexAnalysis();
    }
    @Override
    public void run(Launcher launcher) {
        fogIndexAnalysis.performAnalysis(launcher);
    }

    @Override //TODO: Fix up
    public String toJson() {
        return fogIndexAnalysis.getFogIndex().toString();
    }
}
