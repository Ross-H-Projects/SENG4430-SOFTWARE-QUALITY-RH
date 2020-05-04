package group3.metric_analysis.coupling;

import group3.MetricTracker;
import spoon.Launcher;

public class CouplingTracker extends MetricTracker{
    private CouplingAnalysis couplingAnalysis;

    public CouplingTracker(String[] args) {
        super.doIncludeComments = false;
        couplingAnalysis = new CouplingAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        couplingAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        return String.valueOf(couplingAnalysis.getCouplingTotal());
    }
}
