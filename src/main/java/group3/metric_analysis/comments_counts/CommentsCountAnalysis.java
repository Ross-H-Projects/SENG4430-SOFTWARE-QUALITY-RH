package group3.metric_analysis.comments_counts;

import group3.MetricAnalysis;
import spoon.Launcher;

import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
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

    public HashMap<String, HashMap<String, Integer>> getClassCommentsCountScoresScores() { return classCommentsCountScores;}

    public void performAnalysis (Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, Integer> methodCommentsCountScores = new HashMap<String, Integer>();
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                methodCommentsCountScores.put(methodObject.getSimpleName(), calculateCommentCountForMethod(methodObject));
            }
            classCommentsCountScores.put(classObject.getQualifiedName(), methodCommentsCountScores);
        }
    }

    private int calculateTotalCommentCountForMethod (CtMethod<?> method) {
        int totalComments = method.getElements(new TypeFilter<CtComment>(CtComment.class)).size();
        return totalComments;
    }

    private int calculateDocStringCountForMethod (CtMethod<?> method) {
        int methodDocStringCommentsCount = method.getComments().size();
        return methodDocStringCommentsCount;
    }

    private int calculateInlineCountForMethod (CtMethod<?> method) {
        int totalCommentsCount = this.calculateTotalCommentCountForMethod(method);
        int docStringCommentsCount = this.calculateTotalCommentCountForMethod(method);
        int inlineCommentsCount = totalCommentsCount - docStringCommentsCount;
        return inlineCommentsCount;
    }

    private static ArrayList<CtMethod<?>> getMethods(CtClass<?> classObject){
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }
}


