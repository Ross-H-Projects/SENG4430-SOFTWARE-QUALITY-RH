package group3.metric_analysis.fan_in;

import group3.MetricTracker;
import org.apache.commons.cli.*;
import spoon.Launcher;

import java.util.HashMap;

public class FanInTracker extends MetricTracker {

    private FanInAnalysis fanInAnalysis;

    private Integer upperThreshold = 1;
    private Boolean moduleMode = false;

    public FanInTracker(String[] args)
    {
        Options options = new Options();
        options.addOption("max", true, "Upper threshold of fan in value to display");
        options.addOption("module", false, "Calculate fan in for module rather than methods");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }

        String upperThresholdArg = cmd.getOptionValue("max");
        if (upperThresholdArg != null) {
            try {
                upperThreshold = Integer.parseInt(upperThresholdArg);
            } catch (NumberFormatException e) {
                System.out.println("Fan out lower threshold must be an integer value");
                System.exit(1);
            }
        }
        if (cmd.hasOption("module")) {
            moduleMode = true;
            System.out.println("MODULE");
        }
        fanInAnalysis = new FanInAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        fanInAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {
        if (moduleMode) {
            return moduleModeToJSON();
        } else {
            return methodModeToJSON();
        }
    }

    private String methodModeToJSON() {
        HashMap<String, HashMap<String, Integer>> scores = new HashMap<>();
        HashMap<String, HashMap<String, Integer>> rawScores = fanInAnalysis.getMethodModeFanInScores();
        System.out.println(rawScores);
        for (HashMap.Entry<String, HashMap<String, Integer>> classMap : rawScores.entrySet()) {
            String className = classMap.getKey();
            HashMap<String, Integer> methodScores = new HashMap<>();
            for (HashMap.Entry<String, Integer> methodMap : classMap.getValue().entrySet()) {
                String methodName = methodMap.getKey();
                Integer methodScore = methodMap.getValue();
                System.out.println(methodScore);
                if (methodScore <= upperThreshold) {
                    methodScores.put(methodName, methodScore);
                }
            }
            if (!methodScores.isEmpty()) {
                scores.put(className, methodScores);
            }
        }
        return scores.toString();
    }

    private String moduleModeToJSON() {
        HashMap<String, Integer> scores = new HashMap<>();
        HashMap<String, Integer> rawScores = fanInAnalysis.getModuleModeFanInScores();
        System.out.println(rawScores);
        for (HashMap.Entry<String, Integer> classMap : rawScores.entrySet()) {
            String className = classMap.getKey();
            Integer moduleScore = classMap.getValue();
            System.out.println(moduleScore);
            if (moduleScore <= upperThreshold) {
                scores.put(className, moduleScore);
            }
        }
        return scores.toString();
    }
}
