package group3.metric_analysis.fan_in;

import com.google.gson.Gson;
import group3.MetricTracker;
import org.apache.commons.cli.*;
import spoon.Launcher;

import java.util.HashMap;

public class FanInTracker extends MetricTracker {
    // Fan in tracker launches and reads the results of fan in analysis
    private FanInAnalysis fanInAnalysis;

    // An upper threshold is used to only show methods/modules that have a fan in score lower than or equal to it
    // A default value of 1 is reasonable, but this can easily be overwritten by the user
    private Integer upperThreshold = 1;

    // 2 different modes are provided. Method mode and module mode. This is determined by a boolean value but could be
    // adapted for many options via an enum. The method mode shows a score for each method which includes every invocation pointing to the given method.
    // Module mode produces a score per class, where only invocations that call the given class from an external class (methods in outside classes) are counted
    // Module mode is defaulted to false, meaning method mode is run, can be changed by user
    private Boolean moduleMode = false;

    public FanInTracker(String[] args)
    {
        // Fan in specific arguments are passed in to change default parameters. Defined below and easily extendable
        Options options = new Options();
        options.addOption("max", true, "Upper threshold of fan in value to display");
        options.addOption("module", false, "Calculate fan in for module rather than methods");

        // Parse cmd options
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Overwrite upper threshold if option provided
        String upperThresholdArg = cmd.getOptionValue("max");
        if (upperThresholdArg != null) {
            try {
                upperThreshold = Integer.parseInt(upperThresholdArg);
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
        fanInAnalysis = new FanInAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        // Run the analysis
        fanInAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        // Produce JSON output based on method/module mode choice
        if (moduleMode) {
            return "{\"metric\": \"fan_in\", \"mode\": \"module\", \"result\":" + moduleModeToJSON();
        } else {
            return "{\"metric\": \"fan_in\", \"mode\": \"method\", \"result\":" + methodModeToJSON();
        }
    }

    private String methodModeToJSON() {
        // Recreate a hashmap for each method in analysis that has a score less than or equal to the upper threshold
        HashMap<String, HashMap<String, Integer>> scores = new HashMap<>();
        HashMap<String, HashMap<String, Integer>> rawScores = fanInAnalysis.getMethodModeFanInScores();
        for (HashMap.Entry<String, HashMap<String, Integer>> classMap : rawScores.entrySet()) {
            String className = classMap.getKey();
            HashMap<String, Integer> methodScores = new HashMap<>();
            for (HashMap.Entry<String, Integer> methodMap : classMap.getValue().entrySet()) {
                String methodName = methodMap.getKey();
                Integer methodScore = methodMap.getValue();
                if (methodScore <= upperThreshold) {
                    methodScores.put(methodName, methodScore);
                }
            }
            if (!methodScores.isEmpty()) {
                scores.put(className, methodScores);
            }
        }
        // Create JSON string of hashmap
        Gson gson = new Gson();
        return gson.toJson(scores);
    }

    private String moduleModeToJSON() {
        // Recreate a hashmap for each class in analysis that has a score less than or equal to the upper threshold
        HashMap<String, Integer> scores = new HashMap<>();
        HashMap<String, Integer> rawScores = fanInAnalysis.getModuleModeFanInScores();
        for (HashMap.Entry<String, Integer> classMap : rawScores.entrySet()) {
            String className = classMap.getKey();
            Integer moduleScore = classMap.getValue();
            if (moduleScore <= upperThreshold) {
                scores.put(className, moduleScore);
            }
        }
        // Create JSON string of hashmap
        Gson gson = new Gson();
        return gson.toJson(scores);
    }
}
