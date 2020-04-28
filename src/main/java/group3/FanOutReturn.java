package group3;

import java.util.HashMap;

public class FanOutReturn extends MetricReturn {
    private HashMap<String, HashMap<String, Integer>> fanOutScores;


    public FanOutReturn(HashMap<String, HashMap<String, Integer>> scores) {
        this.fanOutScores = scores;
    }
}
