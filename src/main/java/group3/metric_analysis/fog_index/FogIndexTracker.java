package group3.metric_analysis.fog_index;

import group3.MetricTracker;
import spoon.Launcher;

/**
 * Launches and reads result of FogIndexAnalysis
 * @author DanielSands
 */
public class FogIndexTracker extends MetricTracker {
    private FogIndexAnalysis fogIndexAnalysis;

    public FogIndexTracker(String[] args){
        fogIndexAnalysis = new FogIndexAnalysis();
    }

    /**
     * Runs the analysis
     * @param launcher
     */
    @Override
    public void run(Launcher launcher) {
        fogIndexAnalysis.performAnalysis(launcher);
    }

    /**
     * Produces Json output of metric analysis
     */
    @Override //TODO: Make actual Json with the help of Gson
    public String toJson() {
        return fogIndexAnalysis.getFogIndex().toString();
    }
}
