package group3.metric_analysis.cyclomatic_complexity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import group3.MetricTracker;
import spoon.Launcher;

import java.util.HashMap;

public class CyclomaticTracker extends MetricTracker {

    private CyclomaticAnalysis cyclomaticAnalysis;

    //Constructor
    public CyclomaticTracker(String[] args) {
        cyclomaticAnalysis = new CyclomaticAnalysis();
    }

    //Runs the analysis when called
    @Override
    public void run(Launcher launcher) {
        cyclomaticAnalysis.performAnalysis(launcher);
    }

    //Converts the analysis hashmap into a Json for output
    @Override
    public String toJson() {
        HashMap<String, Integer> outScores = cyclomaticAnalysis.getClassCMCScores();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String output = "\"CyclomaticComplexity\": " + gson.toJson(outScores) + "\n";
        //Returns an extra message to the user at certain values, indicating that the cyclomatic complexity is slightly/far too high
        for (int i = 11; i <9999; i++) {
            if (i < 16) {
                String num = Integer.toString(i);
                output = output.replace(num, num + " - (Complexity slightly high! Consider splitting this function.)");
            } else {
                String num = Integer.toString(i);
                output = output.replace(num, num + " - (Complexity very high! Consider splitting this function.)");
            }
        }
        return output;
    }

    //Returns cyclomatic complexity as an integer for testing purposes
    public int fetchCMC(String filename){
        HashMap<String, Integer> outScores = cyclomaticAnalysis.getClassCMCScores();
        Integer output = outScores.get(filename);
        return output;
    }
}
