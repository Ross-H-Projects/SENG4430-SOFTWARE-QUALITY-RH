package group3.metric_analysis.comments_counts;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
    public HashMap<String, List<HashMap<String , Double>>> getClassCommentAnalysis() {
        return classCommentAnalysis;
    }
    public HashMap<String, HashMap<String, List<HashMap<String , Double>>>> getMethodCommentAnalysis() {
        return methodCommentAnalysis;
    }
    public String toJson() {
        HashMap<String, HashMap<String, List<HashMap<String , Double>>>> classCommentAnalysisDescription = new HashMap<String, HashMap<String, List<HashMap<String , Double>>>>();
        HashMap<String, HashMap<String, HashMap<String, List<HashMap<String , Double>>>>> methodCommentAnalysisDescription = new HashMap<String, HashMap<String, HashMap<String, List<HashMap<String , Double>>>>>();
        classCommentAnalysisDescription.put("Class Comment Analysis", classCommentAnalysis);
        methodCommentAnalysisDescription.put("Method Comment Analysis", methodCommentAnalysis);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonClassString = gson.toJson(classCommentAnalysisDescription);
        String jsonMethodString = gson.toJson(methodCommentAnalysisDescription);

        String json = "";
        String formattedJsonClassString = String.format("{\"Comments Count where class ratio less than or equal to %s%s\": %s}", commentRatioDefault, "%", jsonClassString);
        String formattedJsonMethodString = String.format("{\"Comments Count where method ratio less than or equal to %s%s\": %s}", commentRatioDefault, "%", jsonMethodString);
        if(onAll) {
            return String.format("{\"Comments Count\": [%s\n, %s\n]}", formattedJsonClassString, formattedJsonMethodString);
        } else if (onClass) {
            return formattedJsonClassString;
        } else if (onMethod) {
            return formattedJsonMethodString;
        }
        return json;
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
            classCommentAnalysis.put(classObject.getQualifiedName(), objectsAnalysis);
        }
    }
    public void performMethodAnalysis(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, List<HashMap<String , Double>>> methodComments = new HashMap<String, List<HashMap<String , Double>>>();
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                List<HashMap<String , Double>> objectsAnalysis = runAnaylsisMode(methodObject);
                methodComments.put(methodObject.getSimpleName(), objectsAnalysis);
            }
            methodCommentAnalysis.put(classObject.getQualifiedName(), methodComments);
        }
    }
    public double calcObjectCommentRatio(CtElement object, int totalCommentsCount) {
        int startLine = object.getPosition().getLine();
        int endLine = object.getPosition().getEndLine();
        int totalLineCount = endLine - startLine;
        double ratio;
        if(totalCommentsCount == 0 && totalLineCount == 0) {
            ratio = 100;
        } else {
            ratio = ((double) totalCommentsCount / (double) totalLineCount) * 100;
        }
        return ratio;
    }
    public List<HashMap<String , Double>> runAnaylsisMode(CtElement object) {
        List<HashMap<String , Double>> returnOutput  = new ArrayList<HashMap<String,Double>>();
        HashMap<String , Double> modeAnalysisCount;
        int count = 0;

        modeAnalysisCount = new HashMap<String , Double>();
        count = calculateCountForObject(object);
        double commentRatio = calcObjectCommentRatio(object, count);
        if (commentRatio <= commentRatioDefault) {
            modeAnalysisCount.put("Comment Ratio:", commentRatio);
            returnOutput.add(modeAnalysisCount);

            modeAnalysisCount = new HashMap<String, Double>();
            modeAnalysisCount.put("Comments Count:", (double) count);
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
    public int calculateCountForObject(CtElement object) {
        return Query.getElements(object, new TypeFilter<CtComment>(CtComment.class)).size();
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


