package group3;


public abstract class MetricTracker {
    public enum MetricOutputLevel {
        PROGRAM_LEVEL,
        CLASS_LEVEL,
        METHOD_LEVEL
    }
    public abstract String toJson(MetricOutputLevel outputLevel);
}
