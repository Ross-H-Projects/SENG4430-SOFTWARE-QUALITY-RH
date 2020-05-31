package group3.metric_analysis.coupling;

import group3.MetricTracker;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;

import java.util.HashMap;

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
        // coupling total
        String json = "{'Coupling': {";

        json += "\n\t'Coupling Total': " + couplingAnalysis.getCouplingTotal() + ",";
        json += "\n\t'Weighted Grahp': {";

        HashMap<CtClass, HashMap<CtClass, Integer>> weightedGraph = couplingAnalysis.getCouplingWeightGraph();

        String subJson;
        for (CtClass c1 : weightedGraph.keySet()) {
            json += "\n\t\t'" + c1.getQualifiedName() + "': {";

            for (CtClass c2 : weightedGraph.get(c1).keySet()) {
                if (c1.getQualifiedName().equals(c2.getQualifiedName())) {
                    continue;
                }

                json += "\n\t\t\t'" + c2.getQualifiedName() + "': " + weightedGraph.get(c1).get(c2) + ",";
            }

            if (weightedGraph.get(c1).keySet().size() >= 1) {
                json = json.substring(0, json.length() - 1);
            }

            json += "\n\t\t},";
        }

        if (weightedGraph.keySet().size() >= 1) {
            json = json.substring(0, json.length() - 1);
        }

        json += "\n\t}\n}";

        return json;
    }
}
