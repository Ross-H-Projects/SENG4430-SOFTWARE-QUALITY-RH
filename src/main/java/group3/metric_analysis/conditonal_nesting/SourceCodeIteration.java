package group3.metric_analysis.conditonal_nesting;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.*;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.NamedElementFilter;
import java.util.*;

public class SourceCodeIteration {
    private Launcher launcher;
    private CtClass rootClass;
    public SourceCodeIteration() {
    }
    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }
    public void setRootClass(String className) {
        rootClass = Query.getElements(launcher.getFactory(), new NamedElementFilter<>(CtClass.class, className)).get(0);
    }
    public ArrayList<CtMethod> getClassMethods(CtType currentClass) {
        ArrayList<CtMethod> methods = new ArrayList<CtMethod> (currentClass.getMethods());
        return methods;
    }
    public ArrayList<CtType> getClasses() {
        CtModel model = launcher.getModel();
        ArrayList<CtType> classes = new ArrayList<CtType>(model.getAllTypes());
        return classes;
    }


        ////        CtConstructor b = rootClass.getConstructor();
////        System.out.println(b);
//        Collection methodsCollection = rootClass.getMethods();
//        ArrayList<CtMethod> methodsArrayList = new ArrayList<>(methodsCollection);
//        for (CtMethod bla: methodsArrayList) {
//            System.out.println(bla.getSimpleName());
//        }
//
//        //        List<Object> b = rootClass.filterChildren(new TypeFilter<>(CtIf.class)).list();
//        //        System.out.println(b);
//    }

//    public void iterate() {
//        rootClass.getConstructor()
//    }
//    public void iterateOverConstructor() {
//
//    }
//    public void iterateOverMethod
//    public CtElement getNextElement() {
//        return;
//    }
//    public void checkElementType() {
//    }

}
