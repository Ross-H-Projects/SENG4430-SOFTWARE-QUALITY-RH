package group3.metric_analysis.comments_counts;

import group3.MetricAnalysis;
import group3.MetricReturn;
import group3.Utilities;
import group3.metric_analysis.conditonal_nesting.metric_trackers.ClassTracker;
import group3.metric_analysis.conditonal_nesting.metric_trackers.MethodTracker;
import group3.metric_analysis.conditonal_nesting.metric_trackers.ProgramTracker;
import org.apache.commons.lang3.ObjectUtils;
import spoon.Launcher;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.CtIterator;

public class CommentsCountAnalysis extends MetricAnalysis {
    private ProgramTracker programTracker;

    public CommentsCountAnalysis() {
    }

    @Override
    public void performAnalysis(Launcher launcher) {

    }
}
