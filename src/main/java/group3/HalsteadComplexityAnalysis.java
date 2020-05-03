package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;


import java.util.*;


public class HalsteadComplexityAnalysis extends MetricAnalysis{

    private HashMap<String, Integer> visited_classes;
    private HashMap<String, CtClass<?>> ctClasses;

    private int n1, n2, N1, N2;
    private HashMap<String, Integer> halsteadNumbers;
    private ArrayList<String> halsteadMeasures;


    public HalsteadComplexityAnalysis() {
        visited_classes = new HashMap<String, Integer>();
        ctClasses = new HashMap<String, CtClass<?>>();
        n1 = 0;
        n2 = 0;
        N1 = 0;
        N2 = 0;
        halsteadNumbers = new HashMap<String, Integer>();
        halsteadMeasures = new ArrayList<String>();
    }


    public MetricReturn performAnalysis (String fileName){

        Launcher launcher = Utilities.importCodeSample(fileName);
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));

        for (CtClass<?> c : classes) {
            ctClasses.put(c.getQualifiedName(), c);
        }

        for (CtClass c : classes) {
            if (!visited_classes.containsKey(c.getQualifiedName())) {
                halsteadNumbers = halsteadAnalyser(c);

            }
        }

        HalsteadComplexityReturn x = new HalsteadComplexityReturn();
        return x;
    }

    public HashMap<String, Integer> halsteadAnalyser(CtClass c){
        Set<CtMethod> methodList = c.getMethods();

        HashMap<String, Integer> numbersList = new HashMap<String, Integer>();
        return numbersList;
    }

    private String getEstimatedProgramLength(int n1, int n2){
        return "";
    }

    private String getVolume(int N, int n){
        return "";
    }

    private String getDifficulty(int n1, int n2, int N2){
        return "";
    }

    private String getEffort (int D, int V){
        return "";
    }



}
