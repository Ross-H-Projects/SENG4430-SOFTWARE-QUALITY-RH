package group3.metric_analysis.comments_counts;

import com.google.gson.Gson;
import group3.MetricAnalysis;
import spoon.Launcher;

import spoon.reflect.code.CtBlock;
import spoon.reflect.code.CtComment;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.*;

public class CommentsCountAnalysis extends MetricAnalysis {
    private boolean onAll = false;
    private boolean onClass = false;
    private boolean onMethod = false;

    private int commentRatioDefault;
    private HashMap<String, List<HashMap<String , Double>>> classCommentAnalysis;
    private HashMap<String, HashMap<String, List<HashMap<String , Double>>>> methodCommentAnalysis;

    public CommentsCountAnalysis() {
        this.onAll = false;
        this.onClass = true;
        this.onMethod = false;

        this.commentRatioDefault = 40;

        classCommentAnalysis = new HashMap<String, List<HashMap<String , Double>>>();
        methodCommentAnalysis = new HashMap<String, HashMap<String, List<HashMap<String , Double>>>>();
    }
    public CommentsCountAnalysis(boolean onAll, boolean onClass, boolean onMethod, int maxCommentRatio) {
        this.onAll = onAll;
        this.onClass = onClass;
        this.onMethod = onMethod;

        this.commentRatioDefault = maxCommentRatio;

        classCommentAnalysis = new HashMap<String, List<HashMap<String , Double>>>();
        methodCommentAnalysis = new HashMap<String, HashMap<String, List<HashMap<String , Double>>>>();
    }

    public String toJson() {
        String json = "";
        if(onAll) {
            return String.format("{Comments Analysis where ratio <= %s%s :{%s, %s}}", commentRatioDefault, "%", toJsonClassAnalysis(), toJsonMethodAnalysis());
        } else if (onClass) {
            return toJsonClassAnalysis();
        } else if (onMethod) {
            return toJsonMethodAnalysis();
        }
        return json;
    }
    public String toJsonClassAnalysis() {
        if(classCommentAnalysis.size() > 0) {
            Gson gson = new Gson();
            String json = gson.toJson(classCommentAnalysis);
            return String.format("{%s: %s}", "Class Comments Analysis", json);
        } else {
            return "{}";
        }
    }
    public String toJsonMethodAnalysis() {
        if(methodCommentAnalysis.size() > 0) {
            Gson gson = new Gson();
            String json = gson.toJson(methodCommentAnalysis);
            return String.format("{%s: %s}", "Method Comments Analysis", json);
        } else {
            return "{}";
        }
    }
    public void performAnalysis (Launcher launcher) {
        if (onClass || onAll) {
            performClassAnalysis(launcher);
        }
        if (onMethod || onAll) {
            performMethodAnalysis(launcher);
        }
    }

    public void performClassAnalysis(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            List<HashMap<String , Double>> objectsAnalysis = runAnaylsisMode(classObject);
            classCommentAnalysis.put(classObject.getSimpleName(), objectsAnalysis);
        }
    }
    public void performMethodAnalysis(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, List<HashMap<String , Double>>> methodComments = new HashMap<String, List<HashMap<String , Double>>>();
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                List<HashMap<String , Double>> objectsAnalysis = runAnaylsisMode(methodObject);
                methodComments.put(methodObject.getSimpleName(), objectsAnalysis);
            }
            methodCommentAnalysis.put(classObject.getSimpleName(), methodComments);
        }
    }
    public double calcObjectCommentRatio(CtElement object, int totalCommentsCount) {
        int startLine = object.getPosition().getLine();
        int endLine = object.getPosition().getEndLine();
        int totalLineCount = endLine - startLine;
        double ratio = ((double)totalCommentsCount / (double)totalLineCount) * 100;
        return ratio;
    }
    public List<HashMap<String , Double>> runAnaylsisMode(CtElement object) {
        List<HashMap<String , Double>> returnOutput  = new ArrayList<HashMap<String,Double>>();
        HashMap<String , Double> modeAnalysisCount;
        int count = 0;

        modeAnalysisCount = new HashMap<String , Double>();
        count = calculateInlineCountForMethod(object);
        double commentRatio = calcObjectCommentRatio(object, count);
        if (commentRatio <= commentRatioDefault) {
            modeAnalysisCount.put("Inline Comment Ratio:", commentRatio);
            returnOutput.add(modeAnalysisCount);

            modeAnalysisCount = new HashMap<String, Double>();
            count = calculateInlineCountForMethod(object);
            modeAnalysisCount.put("Inline Comments Count:", (double) count);
            returnOutput.add(modeAnalysisCount);

//            modeAnalysisCount = new HashMap<String, Double>();
//            count = calculateTotalCommentCount(object);
//            modeAnalysisCount.put("Total Comments Count:", (double) count);
//            returnOutput.add(modeAnalysisCount);

//            modeAnalysisCount = new HashMap<String, Double>();
//            count = calculateDocStringCount(object);
//            modeAnalysisCount.put("Doc String Comments Count:", (double) count);
//            returnOutput.add(modeAnalysisCount);
        }
        return returnOutput;
    }
    public int calculateTotalCommentCount(CtElement object) {
        int totalCommentsCount = object.getElements(new TypeFilter<CtComment>(CtComment.class)).size();
        return totalCommentsCount;
    }
    public int calculateDocStringCount(CtElement object) {
        int docStringCommentsCount = object.getComments().size();
        return docStringCommentsCount;
    }
    public int calculateInlineCountForMethod(CtElement object) {
        int totalCommentsCount = calculateTotalCommentCount(object);
        int docStringCommentsCount = calculateDocStringCount(object);
        int inlineCommentsCount = totalCommentsCount - docStringCommentsCount;
        return inlineCommentsCount;
    }
    private static ArrayList<CtMethod<?>> getMethods(CtClass<?> classObject){
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }
}


