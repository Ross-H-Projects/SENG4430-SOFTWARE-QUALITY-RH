package group3;


import static org.junit.Assert.assertEquals;



import group3.metric_analysis.lack_of_cohesion.LackOfCohesionTracker;
import group3.metric_analysis.lack_of_cohesion.LackOfCohesion;
import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import java.util.HashMap;


/**
 * Unit tests for Depth of Inheritance Tree Algorithm.
 */
public class LackOfCohesionTest {

    /**
     * Test the individual outputs for the toy example 1 code sample
     */
    @Test
    public void test1()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\LackOfCohesion2", false);

        LackOfCohesion lackOfCohesion = new LackOfCohesion();

        lackOfCohesion.performAnalysis(launcher);

        HashMap<String, CtClass<?>> resultClasses = lackOfCohesion.getClasses();
        HashMap<String, Integer> resultScores = lackOfCohesion.getClassCohesionScores();
        HashMap<String, Float> resultRatios = lackOfCohesion.getClassCohesionRatios();

        assertEquals("Class Name should be:\n", "Example", resultClasses.get("Example").getQualifiedName());
        assertEquals("Class Score should be:\n", 1, (long) resultScores.get("Example"));
        assertEquals("Class Ratio should be:\n", 0.0f, (float) resultRatios.get("Example"), 0.0000001);
    }

    /**
     * Test the individual outputs for the toy example 1 code sample
     */
    @Test
    public void test2()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\LackOfCohesion3", false);

        LackOfCohesion lackOfCohesion = new LackOfCohesion();

        lackOfCohesion.performAnalysis(launcher);

        HashMap<String, CtClass<?>> resultClasses = lackOfCohesion.getClasses();
        HashMap<String, Integer> resultScores = lackOfCohesion.getClassCohesionScores();
        HashMap<String, Float> resultRatios = lackOfCohesion.getClassCohesionRatios();

        assertEquals("Class Name should be:\n", "A", resultClasses.get("A").getQualifiedName());
        assertEquals("Class Score should be:\n", 0, (long) resultScores.get("A"));
        assertEquals("Class Ratio should be:\n", 1.0f, (float) resultRatios.get("A"), 0.0000001);
    }

    /**
     * Test our results against a 3rd party tool with a toy example
     * The 3rd party tool can be found here: https://github.com/mauricioaniche/ck
     *
     * Note: We are looking at 'tcc; metric outputted from the above mentioned tool. Also we need to comment out the
     * Constructors in the java files being analysed as the 3rd party tool counts them as methods in its calculation of the metric
     * where as we do not.
     */
    @Test
    public void test3rdPartyToyExample() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\LackOfCohesion3", false);

        LackOfCohesion lackOfCohesion = new LackOfCohesion();

        lackOfCohesion.performAnalysis(launcher);

        HashMap<String, CtClass<?>> resultClasses = lackOfCohesion.getClasses();
        HashMap<String, Float> resultRatios = lackOfCohesion.getClassCohesionRatios();

        assertEquals("Class Name should be:\n", "A", resultClasses.get("A").getQualifiedName());
        assertEquals("Class ratio should be:\n", 1.0f, resultRatios.get("A"), 0.01);

        assertEquals("Class Name should be:\n", "B", resultClasses.get("B").getQualifiedName());
        assertEquals("Class ratio should be:\n", 0.66f, resultRatios.get("B"), 0.01);
    }

    /**
     * Test our results against a 3rd party tool with a 'code from the wild' example
     * The 3rd party tool can be found here: https://github.com/mauricioaniche/ck
     *
     * Note: We are looking at 'tcc; metric outputted from the above mentioned tool. Also we need to comment out the
     * Constructors in the java files being analysed as the 3rd party tool counts them as methods in its calculation of the metric
     * where as we do not.
     */
    @Test
    public void test3rdPartyWildExample() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\LackOfCohesion4", false);

        LackOfCohesion lackOfCohesion = new LackOfCohesion();

        lackOfCohesion.performAnalysis(launcher);

        HashMap<String, CtClass<?>> resultClasses = lackOfCohesion.getClasses();
        HashMap<String, Float> resultRatios = lackOfCohesion.getClassCohesionRatios();

        assertEquals("Class Name should be:\n", "BookRunner", resultClasses.get("BookRunner").getQualifiedName());
        assertEquals("Class ratio should be:\n", 1.0f, resultRatios.get("BookRunner"), 0.01);

        assertEquals("Class Name should be:\n", "Book", resultClasses.get("Book").getQualifiedName());
        assertEquals("Class ratio should be:\n", 1.0f, resultRatios.get("Book"), 0.01);

        assertEquals("Class Name should be:\n", "MotorBikeRunner", resultClasses.get("MotorBikeRunner").getQualifiedName());
        assertEquals("Class ratio should be:\n", 1.0f, resultRatios.get("MotorBikeRunner"), 0.01);

        assertEquals("Class Name should be:\n", "MotorBike", resultClasses.get("MotorBike").getQualifiedName());
        assertEquals("Class ratio should be:\n", 0.6f, resultRatios.get("MotorBike"), 0.01);
    }


    /**
     * Test the json output for the toy example 1 code sample
     */
    @Test
    public void test3()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\LackOfCohesion", false);

        LackOfCohesionTracker tester = new LackOfCohesionTracker(new String[0]);
        tester.run(launcher);

        String resultJson = tester.toJson();

        String expectedJson = "{'Lack Of Cohesion': [\n" +
                "\t{\n" +
                "\t\t'Class Name': 'OtherExample',\n" +
                "\t\t'Amount of Methods in class': '3',\n" +
                "\t\t'Coherence Score': '0',\n" +
                "\t\t'Coherence Ratio': '0.67'\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t'Class Name': 'Example',\n" +
                "\t\t'Amount of Methods in class': '2',\n" +
                "\t\t'Coherence Score': '1',\n" +
                "\t\t'Coherence Ratio': '0.00'\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t'Class Name': 'nuddaClass',\n" +
                "\t\t'Amount of Methods in class': '0',\n" +
                "\t\t'Coherence Score': '0',\n" +
                "\t\t'Coherence Ratio': '1.00'\n" +
                "\t}\n" +
                "]}";

        assertEquals("Lack Of Cphesion for code_samples\\test_code_samples\\LackOfCohesion directory must be:\n", expectedJson, resultJson);
    }
}



