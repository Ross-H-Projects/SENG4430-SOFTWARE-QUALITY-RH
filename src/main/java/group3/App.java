package group3;
import org.apache.commons.cli.*;
//import group3.metric_analysis.conditonal_nesting.DepthOfConditionalNestingAnalysis;
import spoon.Launcher;

import java.util.ArrayList;


public class App
{

    private static Launcher launcher;
    private static Launcher launcherNoComments;
    private static Metrics metrics;
    private static Outputs outputs;

    public static void main(String[] args )
    {
        processArgs(args);
        metrics.runMetrics(launcher, launcherNoComments);
        ArrayList<String> metricResults = metrics.getResults();
        outputs.create(metricResults);
    }

    public static void processArgs(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Invalid Arguments");
            System.out.println("Correct Arguments: <SourceFileOrDirectory>  [-m \"metric [metric flags]\"]");
            System.exit(1);
        }

        Options options = new Options();

        options.addOption("m", true, "metric and definitions");
        options.addOption("o", true, "output and definitions");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        metrics = new Metrics(cmd.getOptionValues("m"));

        // If no output options given, default to cmd
        String[] outputOptions = cmd.getOptionValues("o");
        if (outputOptions == null) outputOptions = new String[] {"cmd"};
        outputs = new Outputs(outputOptions);

        launcher = Utilities.importCodeSample(args[0], true);
        launcherNoComments = Utilities.importCodeSample(args[0], false);
    }

//    public static void configInit(Launcher launcher) {
//        group3.ConfigLoader cfg;
//        String codeSampleFileName;
//        String codeSampleFilePath;
//        cfg = new group3.ConfigLoader();
//        codeSampleFileName = cfg.getProperty("codeSampleFileName");
//        codeSampleFilePath = String.format("code_samples/%s", codeSampleFileName);
//        importCodeSample(launcher, codeSampleFilePath);
//    }
//    public static void importCodeSample(Launcher launcher, String codeSampleFilePath) {
//        launcher.addInputResource(codeSampleFilePath);
//        launcher.buildModel();
//        List<CtClass> classList = Query.getElements(launcher.getFactory(), new NamedElementFilter<>(CtClass.class, "WordCount"));
//    }
//    //Class().getAll() returns a wildcard List<CtType<?>> rather than a List<CtClass>
//    //Not sure if its possible to get a more specific type returned
//    public static List<CtType<?>> getAllLauncherClassObjects(Launcher launcher) {
//        return launcher.getFactory().Class().getAll();
//    }
//    public static CtClass getLauncherClassObjectByClassName(Launcher launcher, String className) {
//        CtClass classList = Query.getElements(launcher.getFactory(), new NamedElementFilter<>(CtClass.class, "WordCount")).get(0);
//        return classList;
//    }
//    public static String getClassQualifiedName(CtClass classObject) {
//        return classObject.getQualifiedName();
//    }
//    public static ArrayList<CtMethod> getMethods(CtClass classObject){
//        Collection methodsCollection = classObject.getMethods();
//        ArrayList<CtMethod> methodsArrayList = new ArrayList<>(methodsCollection);
//        return methodsArrayList;
//    }
//    public static CtMethod getMethodByName(CtClass classObject, String methodName) {
//        CtMethod<?> methodObject = (CtMethod<?>) classObject.getMethodsByName(methodName).get(0);
//        return methodObject;
//    }
//    //Temp. Looking at iterating over elements in a method. Still got to make sense of this.
//    public static void iterateOverMethodElements(CtMethod methodObject){
//        CtIterator iterator = new CtIterator(methodObject);
//        while (iterator.hasNext()) {
//            CtElement el = iterator.next();
//            System.out.println(el.toString());
//            //Output role of element in method body
//            System.out.println(el.getRoleInParent());
//            System.out.println("---");
//        }
//
//        //Have to find proper filter applied if using getElements
//        //methodObject.getElements();
//
//        //methodObject.asIterable() achieves same as CtIterator above
////        System.out.println(methodObject.asIterable());
////        for (CtElement bla : methodObject.asIterable()) {
////            System.out.println(bla.prettyprint());
////        }
//    }
}

