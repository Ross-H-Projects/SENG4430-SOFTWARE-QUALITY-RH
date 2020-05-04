package group3.metric_analysis.comments_counts;

import group3.MetricAnalysis;
import spoon.Launcher;

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

    private HashMap<String, List<HashMap<String , Integer>>> classCommentAnalysis;
    private HashMap<String, HashMap<String, List<HashMap<String , Integer>>>> methodCommentAnalysis;

    public CommentsCountAnalysis(boolean onAll, boolean onClass, boolean onMethod) {
        this.onAll = onAll;
        this.onClass = onClass;
        this.onMethod = onMethod;

        classCommentAnalysis = new HashMap<String, List<HashMap<String , Integer>>>();
        methodCommentAnalysis = new HashMap<String, HashMap<String, List<HashMap<String , Integer>>>>();
    }

    public String toJson() {
        String json = "";
        if (onClass || onAll) {
            json+= "\n"+toJsonClassAnalysis();
        }
        if (onMethod || onAll) {
            json+= "\n"+toJsonMethodAnalysis();
        }
        //output methods that dont have docstrings
        //output comment standards
        return json;
    }
    public String toJsonClassAnalysis() {
        String json = "";
        for (Map.Entry mapPair : classCommentAnalysis.entrySet()) {
            String classObject = (String) mapPair.getKey();
            List<Map<String , Integer>> classAnalysisCount = (List<Map<String, Integer>>) mapPair.getValue();
            json+= String.format("{%s: %s} ", classObject, classAnalysisCount.toString());
        }
        return String.format("{%s: %s}", "Class Comments Analysis", json);
    }
    public String toJsonMethodAnalysis() {
        String json = "";
        for (Map.Entry classMapPair : methodCommentAnalysis.entrySet()) {
            String classObject = (String) classMapPair.getKey();
            HashMap<String, List<HashMap<String , Integer>>> classAnalysisCount = (HashMap<String, List<HashMap<String , Integer>>>) classMapPair.getValue();
            for (Map.Entry methodMapPair : classAnalysisCount.entrySet()) {
                String methodObject = (String) methodMapPair.getKey();
                List<HashMap<String , Integer>> methodAnalysisCount = (List<HashMap<String , Integer>>) methodMapPair.getValue();
                json+= String.format("{%s.%s: %s} ", classObject, methodObject, methodAnalysisCount.toString());
            }
        }
        return String.format("{%s: %s}", "Method Comments Analysis", json);
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
            List<HashMap<String , Integer>> objectsAnalysis = runAnaylsisMode(classObject);
            classCommentAnalysis.put(classObject.getSimpleName(), objectsAnalysis);
        }
    }
    public void performMethodAnalysis(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            HashMap<String, List<HashMap<String , Integer>>> methodComments = new HashMap<String, List<HashMap<String , Integer>>>();
            for (CtMethod<?> methodObject : getMethods(classObject)) {
                List<HashMap<String , Integer>> objectsAnalysis = runAnaylsisMode(methodObject);
                methodComments.put(methodObject.getSimpleName(), objectsAnalysis);
            }
            methodCommentAnalysis.put(classObject.getSimpleName(), methodComments);
        }
    }
    public List<HashMap<String , Integer>> runAnaylsisMode(CtElement object) {
        List<HashMap<String , Integer>> returnOutput  = new ArrayList<HashMap<String,Integer>>();
        HashMap<String , Integer> modeAnalysisCount;
        int count = 0;

        modeAnalysisCount = new HashMap<String , Integer>();
        count = calculateTotalCommentCount(object);
        modeAnalysisCount.put("Total Comments Count:", count);
        returnOutput.add(modeAnalysisCount);

        modeAnalysisCount = new HashMap<String , Integer>();
        count = calculateDocStringCount(object);
        modeAnalysisCount.put("Doc String Comments Count:", count);
        returnOutput.add(modeAnalysisCount);

        modeAnalysisCount = new HashMap<String , Integer>();
        count = calculateInlineCountForMethod(object);
        modeAnalysisCount.put("Inline Comments Count:", count);
        returnOutput.add(modeAnalysisCount);

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


