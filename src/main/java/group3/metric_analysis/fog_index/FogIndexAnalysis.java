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

public class FogIndexAnalysis extends MetricAnalysis {

    private HashMap<String, HashMap<String, Double>> classFogAnalysis;
    /**
     * Default constructor
     * initialises private variables
     */
    public FogIndexAnalysis() {
        classFogAnalysis = new HashMap<String, HashMap<String, Double>>();
    }

    public String getFogIndex(){ //TODO: Make nicer, temporary fix for now
        return classFogAnalysis.toString();
    }

    @Override
    public void performAnalysis(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            classFogAnalysis.put(classObject.getSimpleName(), calculateMethodFogIndex(classObject));
        }
    }

    private HashMap<String, Double> calculateMethodFogIndex(CtClass<?> classObject) {
        HashMap<String, Double> methodComments = new HashMap<String, Double>();
        for(CtMethod<?> methodObject : getMethods(classObject)){
            List<CtComment> comments = methodObject.getElements(new TypeFilter<CtComment>(CtComment.class));

            methodComments.put(methodObject.getSimpleName(), calculateFogIndex(comments));
        }
        return methodComments;
    }

    private Double calculateFogIndex(List<CtComment> methodComments){
        double words = 0.0, sentences = 0.0, complexWords = 0.0;
        CountSyllables complexWordsHelper = new CountSyllables();
        for(CtComment comment : methodComments){
            complexWords += complexWordsHelper.count(comment.getContent());
            sentences++; //Simpleton testable.
            words += wordcount(comment.getContent());
        }
        if(words == 0.0){
            return 0.0;
        }
        else{
            return 0.4*(words/sentences + 100*(complexWords/words));
        }
    }

    private static ArrayList<CtMethod<?>> getMethods(CtClass<?> classObject){
        Collection<CtMethod<?>> methodsCollection = classObject.getMethods();
        return new ArrayList<CtMethod<?>>(methodsCollection);
    }

    /**
     * This method was taken from: https://www.javatpoint.com/java-program-to-count-the-number-of-words-in-a-string
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
