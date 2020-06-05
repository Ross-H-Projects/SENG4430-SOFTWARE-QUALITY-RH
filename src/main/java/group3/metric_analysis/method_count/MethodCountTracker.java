package group3.metric_analysis.method_count;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import group3.MetricTracker;
import spoon.Launcher;

import java.util.HashMap;

public class MethodCountTracker extends MetricTracker {

    private MethodCountAnalysis methodAnalysis;

    //Constructor
    public MethodCountTracker(String[] args) {
        methodAnalysis = new MethodCountAnalysis();
    }

    //Runs the analysis when called
    @Override
    public void run(Launcher launcher) {
        methodAnalysis.performAnalysis(launcher);
    }

    //Converts the analysis hashmap into a Json output
    @Override
    public String toJson() {
        HashMap<String, Integer> outScores = methodAnalysis.getClassMethodScores();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return "\"MethodCount\": " + gson.toJson(outScores) + "\n";
    }

    //Returns the method count as an integer, for testing purposes
    public int getCount(String filename){
        int output = methodAnalysis.getClassMethodScores().get(filename);
        return output;
    }
}
