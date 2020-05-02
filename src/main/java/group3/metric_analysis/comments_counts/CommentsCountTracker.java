package group3.metric_analysis.comments_counts;

import group3.MetricTracker;
import spoon.Launcher;

import java.util.HashMap;
import java.util.Map;

public class CommentsCountTracker extends MetricTracker {
    private CommentsCountAnalysis commentsCountAnalysis;

    public CommentsCountTracker(String args) {
        commentsCountAnalysis = new CommentsCountAnalysis();
    }

    @Override
    public void run(Launcher launcher) {

    }

    public String toJson() {
        return "";
    }
}



