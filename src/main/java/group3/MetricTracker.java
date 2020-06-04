package group3;


import spoon.Launcher;

// Abstract metric tracker class
public abstract class MetricTracker {
    protected boolean doIncludeComments = true;

    public abstract void run(Launcher launcher);

    public abstract String toJson();

    public boolean includeComments () {
        return doIncludeComments;
    }
}
