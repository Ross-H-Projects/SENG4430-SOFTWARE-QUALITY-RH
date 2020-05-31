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
     * Test that we can the correct "number of distinct operators" result for maths.java
     */
    @Test
    public void test1()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples/Halstead_Example/h1/maths.java", false);
        HalsteadComplexityAnalysis tester = new HalsteadComplexityAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("Number of distinct operators must be 10 ", 10, (int) tester.getNumbers().get(0));
    }

    /**
     * Test that we can the correct "Volume" result for testcode.java
     */

    public void testWild()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples/Halstead_Example/h2/testcode.java", false);
        HalsteadComplexityAnalysis tester = new HalsteadComplexityAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("The accurate correct volume should be 147.2067179 (casio calculator result), rounded to 147 for ease ", 147, (int) Double.parseDouble(tester.getComplexityMeasures().get("Volume")));

    }
}
