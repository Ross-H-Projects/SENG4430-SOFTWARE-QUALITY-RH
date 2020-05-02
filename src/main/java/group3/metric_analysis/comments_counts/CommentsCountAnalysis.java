package group3.metric_analysis.comments_counts;

import group3.FanOutReturn;
import group3.MetricAnalysis;
import group3.MetricReturn;
import group3.Utilities;
import group3.metric_analysis.conditonal_nesting.metric_trackers.ClassTracker;
import group3.metric_analysis.conditonal_nesting.metric_trackers.MethodTracker;
import group3.metric_analysis.conditonal_nesting.metric_trackers.ProgramTracker;
import org.apache.commons.lang3.ObjectUtils;
import spoon.Launcher;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.CtIterator;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class CommentsCountAnalysis extends MetricAnalysis {
    private HashMap<String, HashMap<String, Integer>> classCommentsCountScores;

    public CommentsCountAnalysis() {
        classCommentsCountScores = new HashMap<String, HashMap<String, Integer>>();
    }

    public MetricReturn performAnalysis (String fileName) {
        Launcher launcher = Utilities.importCodeSample(fileName);

        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, Integer> methodCommentsCountScores = new HashMap<String, Integer>();
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                methodCommentsCountScores.put(methodObject.getSimpleName(), calculateCommentCountForMethod(methodObject));
            }
            classCommentsCountScores.put(classObject.getQualifiedName(), methodCommentsCountScores);
        }

        System.out.println(classCommentsCountScores);

        return (MetricReturn) new FanOutReturn(classCommentsCountScores);
    }

    private int calculateCommentCountForMethod (CtMethod<?> method) {
          return method.getComments().size();
    }

    private static ArrayList<CtMethod<?>> getMethods(CtClass<?> classObject){
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }
}