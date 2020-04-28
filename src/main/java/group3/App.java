package group3;

import group3.metric_analysis.conditonal_nesting.DepthOfConditionalNestingAnalysis;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.visitor.CtIterator;
import spoon.reflect.visitor.Query;
import spoon.reflect.visitor.filter.NamedElementFilter;


import java.util.*;


public class App
{

    public static void main(String[] args )
    {
        processArgs(args);
        performAnalyses(args);

//        Launcher launcher = new Launcher();
//        configInit(launcher);

    }

    public static void processArgs(String[] arguments) {
        if (arguments.length < 2) {
            System.out.println("Error: Invalid Arguments");
            System.out.println("Correct Arguments: <SourceFileOrDirectory> <metric 1>  [[metric 2] .. [metric n]]");
            System.out.println("Example Arguments to place in Intellij run config: code_samples/Inheritance_Example/InheritanceExample.java inheritance_depth");
            System.exit(1);
        }

        // List of metrics that can be passed in via args
        HashSet<String> metrics = new HashSet<String>(Arrays.asList(
                "inheritance_depth",
                "cohesion_score",
                "depth_conditional_nesting"
        ));

        //Checks if all metric args passed in are valid metrics
        for (int i = 1; i < arguments.length; i++) {
            if (!metrics.contains(arguments[i])) {
                System.out.println("Error: Invalid metric");
                System.exit(1);
            }
        }

    }

    public static void performAnalyses(String[] arguments) {

        for (int i = 1; i < arguments.length; i++) {
            switch (arguments[i]) {
                case "inheritance_depth":
                    MetricAnalysis depthInheritanceAnalysis = new DepthInheritanceTreeAnalysis();
                    DepthInheritanceTreeReturn depthInheritanceTreeResults = (DepthInheritanceTreeReturn) depthInheritanceAnalysis.performAnalysis(arguments[0]);

                    System.out.println("Maximum Depth of Inheritance is: " + depthInheritanceTreeResults.getMaxDepth());

                    break;

                case "cohesion_score":
                    MetricAnalysis lackOfCohesion = new LackOfCohesion();
                    LackOfCohesionReturn lackOfCohesionResult = (LackOfCohesionReturn) lackOfCohesion.performAnalysis(arguments[0]);

                    System.out.println("Lack of Cohesion place holder shit");

                    break;
                case "depth_conditional_nesting":
                    MetricAnalysis depthConditionalNesting = new DepthOfConditionalNestingAnalysis();
                    depthConditionalNesting.performAnalysis(arguments[0]);
                    break;
                default:

            }
        }
    }

    public static void configInit(Launcher launcher) {
        group3.ConfigLoader cfg;
        String codeSampleFileName;
        String codeSampleFilePath;
        cfg = new group3.ConfigLoader();
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
    //Temp. Looking at iterating over elements in a method. Still got to make sense of this.
    public static void iterateOverMethodElements(CtMethod methodObject){
        CtIterator iterator = new CtIterator(methodObject);
        while (iterator.hasNext()) {
            CtElement el = iterator.next();
            System.out.println(el.toString());
            //Output role of element in method body
            System.out.println(el.getRoleInParent());
            System.out.println("---");
        }

        //Have to find proper filter applied if using getElements
        //methodObject.getElements();

        //methodObject.asIterable() achieves same as CtIterator above
//        System.out.println(methodObject.asIterable());
//        for (CtElement bla : methodObject.asIterable()) {
//            System.out.println(bla.prettyprint());
//        }
    }
}

