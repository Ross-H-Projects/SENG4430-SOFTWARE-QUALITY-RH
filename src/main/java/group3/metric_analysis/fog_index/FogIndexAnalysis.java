package group3.metric_analysis.fog_index;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author DanielSands
 * Metric analysis for fog index metric
 */
public class FogIndexAnalysis extends MetricAnalysis {

    private HashMap<String, HashMap<String, Double>> classFogAnalysis;
    /**
     * Default constructor
     * initialises private variables
     */
    public FogIndexAnalysis() {
        classFogAnalysis = new HashMap<String, HashMap<String, Double>>();
    }

    public HashMap<String, HashMap<String, Double>> getFogIndex(){ //TODO: Fix this up
        return classFogAnalysis;
    }

    /**
     * Overrides MetricAnalysis abstract method. It initialises analysis process
     * @param launcher
     */
    @Override
    public void performAnalysis(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            classFogAnalysis.put(classObject.getSimpleName(), calculateMethodFogIndex(classObject));
        }
    }

    /**
     * Takes a class object and gets all its methods along with comments for each method
     * @param classObject
     * @return method and its fog index score
     */
    private HashMap<String, Double> calculateMethodFogIndex(CtClass<?> classObject) {
        HashMap<String, Double> methodComments = new HashMap<String, Double>();
        for(CtMethod<?> methodObject : getMethods(classObject)){
            List<CtComment> comments = methodObject.getElements(new TypeFilter<CtComment>(CtComment.class));

            methodComments.put(methodObject.getSimpleName(), calculateFogIndex(comments));
        }
        return methodComments;
    }

    /**
     * Takes comments of a method and calculates its fog index score
     * @param methodComments
     * @return fog index score for that method
     */
    private Double calculateFogIndex(List<CtComment> methodComments){
        double words = 0.0, sentences = 0.0, complexWords = 0.0;
        CountSyllables complexWordsHelper = new CountSyllables();
        if(!methodComments.isEmpty()) {
            for (CtComment comment : methodComments) {
                complexWords += complexWordsHelper.countComplexWords(comment.getContent());
                sentences++; //Assumption: each comment is one sentence.
                words += wordcount(comment.getContent());
            }
        }
            if (words == 0) {
                return 0.0;
            } else {
                return 0.4 * (words / sentences + 100 * (complexWords / words));
            }

    }

    /**
     * Get's all the methods of a class object
     * @param classObject
     * @return List of CtMethods
     */
    private static ArrayList<CtMethod<?>> getMethods(CtClass<?> classObject){
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }

    /**
     * This method was taken from: https://www.javatpoint.com/java-program-to-count-the-number-of-words-in-a-string //TODO: Reference properly
     * @param string
     * @return
     */
    static int wordcount(String string)
    {
        int count=0;

        char ch[]= new char[string.length()];
        for(int i=0;i<string.length();i++)
        {
            ch[i]= string.charAt(i);
            if( ((i>0)&&(ch[i]!=' ')&&(ch[i-1]==' ')) || ((ch[0]!=' ')&&(i==0)) )
                count++;
        }
        return count;
    }
}
