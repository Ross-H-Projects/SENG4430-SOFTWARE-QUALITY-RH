package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.NamedElementFilter;

import java.util.List;

public class Utilities {
    public static Launcher importCodeSample (String fileName) {
        Launcher launcher = new Launcher();
        launcher.addInputResource(fileName);
        launcher.buildModel();

        return launcher;
    }

}
