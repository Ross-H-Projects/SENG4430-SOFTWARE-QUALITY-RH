package group3;


import static org.junit.Assert.assertEquals;


import group3.metric_analysis.halstead_complexity.HalsteadComplexityAnalysis;
import group3.metric_analysis.halstead_complexity.HalsteadComplexityTracker;
import org.junit.Test;
import spoon.Launcher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class HalsteadComplexityTest {


    /**
     * Test that we can receive the correct "number of distinct operators" and "Number of total operands" results for maths.java
     */
    @Test
    public void test1()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples/Halstead_Example/h1/maths.java", false);
        HalsteadComplexityAnalysis tester = new HalsteadComplexityAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("Number of distinct operators must be 10 ", 10, (int) tester.getNumbers().get(0));
        assertEquals("Number of total operands must be 22 ", 22, (int) tester.getNumbers().get(3));
    }

    /**
     * Test that we can receive the correct "Program vocabulary" and "Estimated program length" for maths.java
     */
    @Test
    public void test2()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples/Halstead_Example/h1/maths.java", false);
        HalsteadComplexityAnalysis tester = new HalsteadComplexityAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("Program vocabulary must be 24 ", 24,  Integer.parseInt(tester.getComplexityMeasures().get("Program vocabulary")));
        assertEquals("Estimated program length must be 86 (double value rounded down) ", 86,  (int) Double.parseDouble(tester.getComplexityMeasures().get("Estimated program length")));
    }

    /**
     * Test the json output for the forThe.java test code
     */
    @Test
    public void testJson()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples/Halstead_Example/h3/forThe.java", false);
        HalsteadComplexityTracker tester = new HalsteadComplexityTracker(new String[0]);
        tester.run(launcher);

        String resultJson = tester.toJson();

        String expectedJson = "{'Halstead Complexity': {\n" +
                "\t'Halstead Numbers': {\n" +
                "\t\t'number of distinct operators (n1)': 9,\n" +
                "\t\t'number of distinct operands (n2)': 13,\n" +
                "\t\t'total number of operators (N1)': 12,\n" +
                "\t\t'total number of operands (N2)': 18,\n" +
                "\t\t},\n" +
                "\t'Halstead Complexity Measures': {\n" +
                "\t\t'Diffulty': 4.5,\n" +
                "\t\t'Volume': 133.78294855911892,\n" +
                "\t\t'Time required to program': 33.44573713977973(sec),\n" +
                "\t\t'Effort': 602.0232685160352,\n" +
                "\t\t'Program vocabulary': 22,\n" +
                "\t\t'Program length': 30,\n" +
                "\t\t'Estimated program length': 76.63504134881501,\n" +
                "\t\t'Delivered bugs': 0.02376589989430029,\n" +
                "\t\t},\n" +
                "\t}\n" +
                "}";

        assertEquals("Coupling for code_samples/Halstead_Example/h3/forThe.java directory must be:\n", expectedJson, resultJson);
    }

    /**
     * Test that we can the correct "Volume" result for a code from the wild: testcode.java
     */

    @Test
    public void testWild()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples/Halstead_Example/h2/testcode.java", false);
        HalsteadComplexityAnalysis tester = new HalsteadComplexityAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("The accurate correct volume should be 127.4378254033075 (casio calculator result), rounded to 127 for ease ", 127, (int) Double.parseDouble(tester.getComplexityMeasures().get("Volume")));

    }

    /**
     * Test third party tool "number of distinct operators" result (https://github.com/aametwally/Halstead-Complexity-Measures) with first party tool
     */

    @Test
    public void testThirdParty(){
        Launcher launcher = Utilities.importCodeSample("code_samples/Halstead_Example/h1/maths.java", false);
        HalsteadComplexityAnalysis tester = new HalsteadComplexityAnalysis();
        tester.performAnalysis(launcher);

        //Third party tool counts equals signs '=' and ':' in addition to the operators this first party tool captures
        //3PT produced these numbers:
        //Overall Distinct Operators in the directory= 10
        //Overall Distinct Operands in the directory= 16
        //Overall Total Operators in the directory= 16
        //Overall Total Operands in the directory= 32
        //We will test number of distinct operators
        assertEquals("Number of distinct operators with third party tool was 10, first party tool must count 10 ", 10, (int) tester.getNumbers().get(0));
    }

}
