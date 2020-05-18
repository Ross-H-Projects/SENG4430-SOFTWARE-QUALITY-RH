package group3;


import static org.junit.Assert.assertEquals;


import group3.LackOfCohesion;
import group3.metric_analysis.depth_inheritance_tree.DepthInheritanceTreeAnalysis;
import group3.metric_analysis.depth_inheritance_tree.DepthInheritanceTreeTracker;
import group3.metric_analysis.lack_of_cohesion.LackOfCohesionTracker;
import org.junit.Test;
import spoon.Launcher;
import spoon.reflect.declaration.CtClass;


import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

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
     * Test the json output for the toy example 1 code sample
     */
    @Test
    public void test2()
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



