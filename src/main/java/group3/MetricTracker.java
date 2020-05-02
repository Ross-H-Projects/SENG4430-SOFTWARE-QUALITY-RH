package group3;


import spoon.Launcher;

public abstract class MetricTracker {
    public abstract void run(Launcher launcher);

    public abstract String toJson();
}
