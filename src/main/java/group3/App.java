package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.NamedElementFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class App
{
    public static void main(String[] args )
    {
        Launcher launcher = new Launcher();
        configInit(launcher);
//        CtClass a = getLauncherClassObjectByClassName(launcher, "WordCount");
    }
    public static void configInit(Launcher launcher) {
        ConfigLoader cfg;
        String codeSampleFileName;
        String codeSampleFilePath;
        cfg = new ConfigLoader();
        codeSampleFileName = cfg.getProperty("codeSampleFileName");
        codeSampleFilePath = String.format("code_samples/%s", codeSampleFileName);
        importCodeSample(launcher, codeSampleFilePath);
    }
    public static void importCodeSample(Launcher launcher, String codeSampleFilePath) {
        launcher.addInputResource(codeSampleFilePath);
        launcher.buildModel();
        List<CtClass> classList = Query.getElements(launcher.getFactory(), new NamedElementFilter<>(CtClass.class, "WordCount"));
    }
    //Class().getAll() returns a wildcard List<CtType<?>> rather than a List<CtClass>
    //Not sure if its possible to get a more specific type returned
    public static List<CtType<?>> getAllLauncherClassObjects(Launcher launcher) {
        return launcher.getFactory().Class().getAll();
    }
    public static CtClass getLauncherClassObjectByClassName(Launcher launcher, String className) {
        CtClass classList = Query.getElements(launcher.getFactory(), new NamedElementFilter<>(CtClass.class, "WordCount")).get(0);
        return classList;
    }
    public static String getClassQualifiedName(CtClass classObject) {
        return classObject.getQualifiedName();
    }
    public static ArrayList<CtMethod> getMethods(CtClass classObject){
        Collection methodsCollection = classObject.getMethods();
        ArrayList<CtMethod> methodsArrayList = new ArrayList<>(methodsCollection);
        return methodsArrayList;
    }
    public static CtMethod getMethodByName(CtClass classObject, String methodName) {
        CtMethod<?> methodObject = (CtMethod<?>) classObject.getMethodsByName(methodName).get(0);
        return methodObject;
    }
    //get getters of variables
    //get variables
    //get comments
    //other stuffs

// Commented out for loop print of objects for now
//        // list all packages of the model
//        for(CtPackage p : model.getAllPackages()) {
//            System.out.println("package: " + p.getQualifiedName());
//        }
//        // list all classes of the model
//        for(CtType<?> s : model.getAllTypes()) {
//            System.out.println("class: " + s.getQualifiedName());
//        }
//
//        // list all classes (different method)
//        for (CtType<?> ctClass : launcher.getFactory().Class().getAll()) {
//            System.out.println("class: " + ctClass.getQualifiedName());
//        }
}