package group3.metric_analysis.conditonal_nesting;

import spoon.Launcher;
import spoon.reflect.code.CtIf;
import spoon.reflect.declaration.*;
import spoon.reflect.visitor.Filter;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.chain.CtQuery;
import spoon.reflect.visitor.filter.NamedElementFilter;
import spoon.reflect.visitor.filter.TypeFilter;

import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
