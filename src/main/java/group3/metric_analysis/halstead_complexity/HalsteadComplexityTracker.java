package group3.metric_analysis.halstead_complexity;

import group3.MetricTracker;
import spoon.Launcher;
import java.util.*;

public class HalsteadComplexityTracker extends MetricTracker {
    private HalsteadComplexityAnalysis halsteadComplexityAnalysis;

    public HalsteadComplexityTracker(String[] args) {
        super.doIncludeComments = false;
        halsteadComplexityAnalysis = new HalsteadComplexityAnalysis();
    }

    @Override
    public void run(Launcher launcher) {
        halsteadComplexityAnalysis.performAnalysis(launcher);
    }

    @Override
    public String toJson() {

        String json = "{'Halstead Complexity': {";

        json += "\n\t'Halstead Numbers': {";
        json += "\n\t\t'number of distinct operators (n1)': "   +halsteadComplexityAnalysis.getNumbers().get(0)+",";
        json += "\n\t\t'number of distinct operands (n2)': "    +halsteadComplexityAnalysis.getNumbers().get(1)+",";
        json += "\n\t\t'total number of operators (N1)': "      +halsteadComplexityAnalysis.getNumbers().get(2)+",";
        json += "\n\t\t'total number of operands (N2)': "       +halsteadComplexityAnalysis.getNumbers().get(3)+",";
        json += "\n\t\t},";


        json += "\n\t'Halstead Complexity Measures': {";
        for (Map.Entry<String, String> entry : halsteadComplexityAnalysis.getComplexityMeasures().entrySet()) {
            if(entry.getKey().equals("Time required to program")){
                json += "\n\t\t'"+entry.getKey()+"': "+entry.getValue()+"(sec),";
            }
            else {
                json += "\n\t\t'" + entry.getKey() + "': " + entry.getValue() + ",";
            }
        }
        json += "\n\t\t},";
        json += "\n\t}\n}";


        return json;

    }
}


