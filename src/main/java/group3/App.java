package group3;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.*;
import spoon.reflect.visitor.filter.TypeFilter;

import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        ConfigLoader cfg;
        String codeSampleFileName;
        String codeSampleFilePath;

        cfg = new ConfigLoader();
        codeSampleFileName = cfg.getProperty("codeSampleFileName");
        System.out.println(codeSampleFileName);

        Launcher launcher = new Launcher();
        launcher.addInputResource(codeSampleFileName);
        launcher.buildModel();
        CtModel model = launcher.getModel();

        // list all packages of the model
        for(CtPackage p : model.getAllPackages()) {
            System.out.println("package: " + p.getQualifiedName());
        }
        // list all classes of the model
        for(CtType<?> s : model.getAllTypes()) {
            System.out.println("class: " + s.getQualifiedName());
        }

        // list all classes (different method)
        for (CtType<?> ctClass : launcher.getFactory().Class().getAll()) {
            System.out.println("class: " + ctClass.getQualifiedName());
        }

    }
}