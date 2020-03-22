package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

public class DepthInheritanceTreeAnalysis extends MetricAnalysis {

    public DepthInheritanceTreeAnalysis() {

    }

    public int performAnalysis (String fileName) {
        System.out.println("accessing performAnalysis in DepthInheritanceTreeAnalysis..");


        Launcher launcher = Utilities.importCodeSample(fileName);

        List<CtClass> a = Query.getElements(launcher.getFactory(), new TypeFilter<CtClass>(CtClass.class));

        for (CtClass c : a) {
            System.out.println(c.getQualifiedName());
            if (c.getSuperclass() != null) {
                System.out.println(c.getSuperclass().getQualifiedName());
            }
            System.out.println("------------------");
        }

        return 0;
    }
}
