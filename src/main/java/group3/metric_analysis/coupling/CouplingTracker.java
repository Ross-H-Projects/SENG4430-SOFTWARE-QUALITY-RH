package group3.metric_analysis.coupling;

import group3.MetricTracker;
import org.apache.commons.cli.*;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;

import java.util.HashMap;

public class CouplingTracker extends MetricTracker{
    private CouplingAnalysis couplingAnalysis;

    private Integer lowerThreshold = 0;

    private Boolean thresholdSpecified = false;

    public CouplingTracker(String[] args) {
        super.doIncludeComments = false;

        Options options = new Options();
        options.addOption("min", true, "Lower threshold of coupling value to display");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Overwrite lower threshold if option provided
        String lowerThresholdArg = cmd.getOptionValue("min");
        if (lowerThresholdArg != null) {
            try {
                lowerThreshold = Integer.parseInt(lowerThresholdArg);
                thresholdSpecified = true;
            } catch (NumberFormatException e) {
                System.out.println("Fan out lower threshold must be an integer value");
                System.exit(1);
            }
        }

        couplingAnalysis = new CouplingAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        couplingAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        // Produce JSON output based on method/module mode choice
        if (thresholdSpecified) {
            return lowerThresholdToJson();
        } else {
            return fullToJson();
        }
    }

    public String lowerThresholdToJson(){

        String json = "{'Lower Threshold': "+lowerThreshold+"}\n";

        json += "\n{'Coupling': {";
        json += "\n\t'Coupling Total': " + couplingAnalysis.getCouplingTotal() + ",";
        json += "\n\t'Weighted Graph': {";

        HashMap<CtClass, HashMap<CtClass, Integer>> weightedGraph = couplingAnalysis.getCouplingWeightGraph();

        int i = 0;
        String subJson;
        for (CtClass c1 : weightedGraph.keySet()) {
            i++;
            //System.out.println(c1.getQualifiedName());
            //System.out.println(i + "out of " + weightedGraph.size() + " completed");

            json += "\n\t\t'" + c1.getQualifiedName() + "': {";

            for (CtClass c2 : weightedGraph.get(c1).keySet()) {

                if(weightedGraph.get(c1).get(c2)>lowerThreshold){
                    if (c1.getQualifiedName().equals(c2.getQualifiedName())) {
                        continue;
                    }

                    json += "\n\t\t\t'" + c2.getQualifiedName() + "': " + weightedGraph.get(c1).get(c2) + ",";
                }

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

    public String fullToJson() {

        String json = "{'Coupling': {";

        json += "\n\t'Coupling Total': " + couplingAnalysis.getCouplingTotal() + ",";
        json += "\n\t'Weighted Graph': {";

        HashMap<CtClass, HashMap<CtClass, Integer>> weightedGraph = couplingAnalysis.getCouplingWeightGraph();

        int i = 0;
        String subJson;
        for (CtClass c1 : weightedGraph.keySet()) {
            i++;
            //System.out.println(c1.getQualifiedName());
            //System.out.println(i + "out of " + weightedGraph.size() + " completed");

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
