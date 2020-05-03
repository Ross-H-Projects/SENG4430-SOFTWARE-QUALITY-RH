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
    private HashMap<String, HashMap<String, Integer>> classCommentsTotalCountScores;
    private HashMap<String, HashMap<String, Integer>> classCommentsDocStringCountScores;
    private HashMap<String, HashMap<String, Integer>> classCommentsInlineCountScores;

    public CommentsCountAnalysis() {
        classCommentsTotalCountScores = new HashMap<String, HashMap<String, Integer>>();
        classCommentsDocStringCountScores = new HashMap<String, HashMap<String, Integer>>();
        classCommentsInlineCountScores = new HashMap<String, HashMap<String, Integer>>();
    }

    public HashMap<String, HashMap<String, Integer>> getClassCommentsTotalCountScores() { return classCommentsTotalCountScores;}
    public HashMap<String, HashMap<String, Integer>> getClassCommentsDocStringCountScores() { return classCommentsDocStringCountScores;}
    public HashMap<String, HashMap<String, Integer>> getClassCommentsInlineCountScores() { return classCommentsInlineCountScores;}

    public void performAnalysis (Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, Integer> methodCommentsTotalCountScores = new HashMap<String, Integer>();
            HashMap<String, Integer> methodCommentsDocStringCountScores = new HashMap<String, Integer>();
            HashMap<String, Integer> methodCommentsInlineCountScores = new HashMap<String, Integer>();

            for (CtMethod<?> methodObject : getMethods(classObject)) {
                methodCommentsTotalCountScores.put(methodObject.getSimpleName(), calculateTotalCommentCountForMethod(methodObject));
                methodCommentsDocStringCountScores.put(methodObject.getSimpleName(), calculateDocStringCountForMethod(methodObject));
                methodCommentsInlineCountScores.put(methodObject.getSimpleName(), calculateInlineCountForMethod(methodObject));
            }
            classCommentsTotalCountScores.put(classObject.getQualifiedName(), methodCommentsTotalCountScores);
            classCommentsDocStringCountScores.put(classObject.getQualifiedName(), methodCommentsDocStringCountScores);
            classCommentsInlineCountScores.put(classObject.getQualifiedName(), methodCommentsInlineCountScores);
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
        int docStringCommentsCount = this.calculateDocStringCountForMethod(method);
        int inlineCommentsCount = totalCommentsCount - docStringCommentsCount;
        return inlineCommentsCount;
    }

    private static ArrayList<CtMethod<?>> getMethods(CtClass<?> classObject){
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }
}


