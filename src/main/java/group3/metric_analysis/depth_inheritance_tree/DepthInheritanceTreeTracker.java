package group3.metric_analysis.depth_inheritance_tree;

import group3.MetricTracker;
import spoon.Launcher;

public class DepthInheritanceTreeTracker extends MetricTracker {
    private DepthInheritanceTreeAnalysis depthInheritanceTreeAnalysis;

    public DepthInheritanceTreeTracker(String args) {
        depthInheritanceTreeAnalysis = new DepthInheritanceTreeAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        depthInheritanceTreeAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        return String.valueOf(depthInheritanceTreeAnalysis.getMaxDepth());
    }
}
