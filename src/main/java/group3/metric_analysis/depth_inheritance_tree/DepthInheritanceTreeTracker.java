package group3.metric_analysis.depth_inheritance_tree;

import group3.MetricTracker;
import spoon.Launcher;

import java.util.HashMap;
import java.util.LinkedList;

public class DepthInheritanceTreeTracker extends MetricTracker {
    private DepthInheritanceTreeAnalysis depthInheritanceTreeAnalysis;

    public DepthInheritanceTreeTracker(String[] args) {
        super.doIncludeComments = false;
        depthInheritanceTreeAnalysis = new DepthInheritanceTreeAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        depthInheritanceTreeAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        String depth = String.valueOf(depthInheritanceTreeAnalysis.getMaxDepth());
        HashMap<String, LinkedList<String>> classInheritanceChains = depthInheritanceTreeAnalysis.getClassInheritanceChains();

        String json = String.format("{'Depth Of Inheritance': \n{\t'score': '%s',", depth);
        json += "\n\t'chains': [";

        String subJson;
        for (String k : classInheritanceChains.keySet()) {


            subJson = "\n\t\t[";
            for (String c : classInheritanceChains.get(k)) {
                subJson += " '" + c + "',";
            }
            if (classInheritanceChains.get(k).size() >= 1) {
                subJson = subJson.substring(0, subJson.length() - 1);
            }

            subJson += " ]";

            json += subJson + ",";
        }

        // remove last comma from json
        if (classInheritanceChains.keySet().size() >= 1) {
            json = json.substring(0, json.length() - 1);
        }

        json += "\n\t]}\n}";
        return json;
    }
}
