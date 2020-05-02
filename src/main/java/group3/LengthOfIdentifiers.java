package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.HashMap;
import java.util.List;

public class LengthOfIdentifiers extends MetricAnalysis {

    private HashMap<CtClass<?>, Double > classAverageLengthOfIdentifiers; //Here each class' average length of identifiers will be stored
    private HashMap<CtClass<?>, String > tooShortIdentifier; //Average length of identifier doesn't say much, this will store all identifiers that have a length less than 4

    @Override
    public MetricReturn performAnalysis(String fileName) {
        Launcher launcher = Utilities.importCodeSample(fileName);
        List<CtClass<?>> classes = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass<?>>(CtClass.class));
        for (CtClass<?> c : classes) {
            //Loads of "get" methods that can be used on c, need to figure out relevant ones for me
            //TODO: Within class, access all method names, parameter names and variable names
            //TODO: Maybe add something so that if any identifier is less than 3 letters long
            //      the user of this system should get a warning and exact location of identifier?
        }
        //TODO: Calculate average length of identifiers
        return null;
    }

    private int calculateLengthOfIdentifiers(CtClass<?> currentClass){
        return 0; //TODO: Complete this. Should this even be an int?
    }
}
