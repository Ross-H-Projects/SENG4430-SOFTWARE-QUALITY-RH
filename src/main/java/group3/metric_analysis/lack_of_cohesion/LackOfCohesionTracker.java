package group3.metric_analysis.lack_of_cohesion;

import group3.LackOfCohesion;
import group3.MetricTracker;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;

import java.util.HashMap;

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
        HashMap<String, Integer> classCohesionScores = lackOfCohesion.getClassCohesionScores();
        HashMap<String, Float> classCohesionRatios = lackOfCohesion.getClassCohesionRatios();
        HashMap<String, CtClass<?>> classes = lackOfCohesion.getClasses();

        String json = "{'Lack Of Cohesion': [";

        String subJson;
        for (String className : classes.keySet()) {
            subJson = "\n\t{";
            subJson += String.format("\n\t\t'Class Name': '%s',", className);
            subJson += String.format("\n\t\t'Amount of Methods in class': '%s',", classes.get(className).getMethods().size());
            subJson += String.format("\n\t\t'Coherence Score': '%s',", classCohesionScores.get(className));
            subJson += String.format("\n\t\t'Coherence Ratio': '%.2f'", classCohesionRatios.get(className));
            subJson += "\n\t},";

            json += subJson;
        }

        // remove last comma from json
        json = json.substring(0, json.length() - 1);
        json += "\n]}";
        return json;
    }
}
