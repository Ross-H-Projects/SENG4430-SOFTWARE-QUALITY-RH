package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.LinkedList;
import java.util.List;

public class DepthInheritanceTreeAnalysis extends MetricAnalysis {



    public DepthInheritanceTreeAnalysis() {

    }

    public MetricReturn performAnalysis (String fileName) {
//        System.out.println("accessing performAnalysis in DepthInheritanceTreeAnalysis..");
//
//
//        Launcher launcher = Utilities.importCodeSample(fileName);
//
//        List<CtClass> a = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass>(CtClass.class));
//
//        List<Node> branches = new LinkedList<Node>();
//
//        for (CtClass c : a) {
//
//
//
//            System.out.println(c.getQualifiedName());
//            if (c.getSuperclass() != null) {
//                System.out.println(c.getSuperclass().getQualifiedName());
//            }
//            System.out.println("------------------");
//        }
//
//        return 0;

        DepthInheritanceTreeReturn x = new DepthInheritanceTreeReturn();
        x.setA(33);

        return x;
    }


}
