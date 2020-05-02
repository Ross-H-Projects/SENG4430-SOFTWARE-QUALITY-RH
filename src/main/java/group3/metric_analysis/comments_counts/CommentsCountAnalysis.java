package group3.metric_analysis.comments_counts;

import group3.MetricAnalysis;
import spoon.Launcher;

import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class CommentsCountAnalysis extends MetricAnalysis {
    private HashMap<String, HashMap<String, Integer>> classCommentsCountScores;

    public CommentsCountAnalysis() {
        classCommentsCountScores = new HashMap<String, HashMap<String, Integer>>();
    }


    public void performAnalysis (Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, Integer> methodCommentsCountScores = new HashMap<String, Integer>();
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                methodCommentsCountScores.put(methodObject.getSimpleName(), calculateCommentCountForMethod(methodObject));
            }
            classCommentsCountScores.put(classObject.getQualifiedName(), methodCommentsCountScores);
        }

        System.out.println(classCommentsCountScores);

    }

    private int calculateCommentCountForMethod (CtMethod<?> method) {
          return method.getComments().size();
    }

    private static ArrayList<CtMethod<?>> getMethods(CtClass<?> classObject){
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }
}