package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

public class LengthOfIdentifiers extends MetricAnalysis {

    @Override
    public MetricReturn performAnalysis(String fileName) {
        Launcher launcher = Utilities.importCodeSample(fileName);
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));
        //TODO: Calculate length of identifiers
        return null;
    }

    private int calculateLengthOfIdentifiers(CtClass<?> currentClass){
        return 0; //TODO: Complete this. Should this even be an int?
    }
}
