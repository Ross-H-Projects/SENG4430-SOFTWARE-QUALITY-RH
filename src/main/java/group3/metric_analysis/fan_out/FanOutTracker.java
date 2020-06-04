package group3.metric_analysis.fan_out;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import group3.MetricTracker;
import org.apache.commons.cli.*;
import spoon.Launcher;

import java.util.HashMap;

public class FanOutTracker extends MetricTracker {
    // Fan out tracker launches and reads the results of fan out analysis
    private FanOutAnalysis fanOutAnalysis;

    // A lower threshold is used to only show methods/modules that have a fan out score greater than or equal to it
    // A default value of 5 is reasonable, but this can easily be overwritten by the user
    private Integer lowerThreshold = 5;

    // 2 different modes are provided. Method mode and module mode. This is determined by a boolean value but could be
    // adapted for many options via an enum. The method mode shows a score for each method which includes every invocation within.
    // Module mode produces a score per class, where only invocations that call external code (methods in outside classes) are counted
    // Module mode is defaulted to false, meaning method mode is run, can be changed by user
    private Boolean moduleMode = false;

    public FanOutTracker(String[] args) {
        // Fan out specific arguments are passed in to change default parameters. Defined below and easily extendable
        Options options = new Options();
        options.addOption("min", true, "Lower threshold of fan out value to display");
        options.addOption("module", false, "Calculate fan out for module rather than methods");

        // Parse cmd options
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
            } catch (NumberFormatException e) {
                System.out.println("Fan out lower threshold must be an integer value");
                System.exit(1);
            }
        }
        // Set module mode if option provided
        if (cmd.hasOption("module")) {
            moduleMode = true;
        }

        // Initialise the analyser
        fanOutAnalysis = new FanOutAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        // Run the analysis
        fanOutAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        // Produce JSON output based on method/module mode choice
        if (moduleMode) {
            return "{\"Fan Out\": {\n\t\"mode\": \"module\",\n\t\"result\":" + moduleModeToJSON() + "\n}}";
        } else {
            return "{\"Fan Out\": {\n\t\"mode\": \"method\",\n\t\"result\":" + methodModeToJSON() + "\n}}";
        }

    }

    private String methodModeToJSON() {
        // Recreate a hashmap for each method in analysis that has a score greater than or equal to the lower threshold
        HashMap<String, HashMap<String, Integer>> scores = new HashMap<>();
        HashMap<String, HashMap<String, Integer>> rawScores = fanOutAnalysis.getMethodModeScores();
        for (HashMap.Entry<String, HashMap<String, Integer>> classMap : rawScores.entrySet()) {
            String className = classMap.getKey();
            HashMap<String, Integer> methodScores = new HashMap<>();
            for (HashMap.Entry<String, Integer> methodMap : classMap.getValue().entrySet()) {
                String methodName = methodMap.getKey();
                Integer methodScore = methodMap.getValue();
                if (methodScore >= lowerThreshold) {
                    methodScores.put(methodName, methodScore);
                }
            }
            if (!methodScores.isEmpty()) {
                scores.put(className, methodScores);
            }
        }
        // Create JSON string of hashmap
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(scores);
    }

    private String moduleModeToJSON() {
        // Recreate a hashmap for each class in analysis that has a score greater than or equal to the lower threshold
        HashMap<String, Integer> scores = new HashMap<>();
        HashMap<String, Integer> rawScores = fanOutAnalysis.getModuleModeScores();
        for (HashMap.Entry<String, Integer> classMap : rawScores.entrySet()) {
            String className = classMap.getKey();
            Integer moduleScore = classMap.getValue();
            if (moduleScore >= lowerThreshold) {
                scores.put(className, moduleScore);
            }
        }
        // Create JSON string of hashmap
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(scores);
    }
}
