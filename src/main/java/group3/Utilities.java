package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.NamedElementFilter;

import java.util.List;

public class Utilities {
    // helper method to create launcher from code
    public static Launcher importCodeSample (String fileName, boolean includeComments) {
        Launcher launcher = new Launcher();

        if (!includeComments) {
            launcher.getEnvironment().setCommentEnabled(false);
        }

        launcher.addInputResource(fileName);
        launcher.buildModel();

        return launcher;
    }

}
