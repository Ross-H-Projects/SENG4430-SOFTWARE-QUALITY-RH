package group3.metric_analysis.fog_index;

import group3.MetricAnalysis;
import spoon.Launcher;
import spoon.reflect.code.CtComment;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashMap;
import java.util.List;

public class FogIndexAnalysis extends MetricAnalysis {

    private HashMap<String, String> classFogAnalysis;
    /**
     * Default constructor
     * initialises private variables
     */
    public FogIndexAnalysis() {
        classFogAnalysis = new HashMap<String, String>(); //TODO: Decide how it should be stored, this is more temporary
    }

    public String getReturn(){
        return classFogAnalysis.toString();
    }

    @Override
    public void performAnalysis(Launcher launcher) {
        for (CtClass<?> classObject : Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class))) {
            classFogAnalysis.put(classObject.getSimpleName(), calculateClassFogIndex(classObject));
        }
    }

    private String calculateClassFogIndex(CtClass<?> classObject) {
        //TODO: Actually calculate fog_index, instead of sentences maybe base it off new lines in comments. So one line = one sentence?
        return classObject.getElements(new TypeFilter<CtComment>(CtComment.class)).toString(); //This gives all comments
    }
}
