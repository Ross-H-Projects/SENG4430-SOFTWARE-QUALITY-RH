package group3;


import group3.metric_analysis.fan_out.FanOutAnalysis;
import org.junit.Test;
import spoon.Launcher;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for Depth of Inheritance Tree Algorithm.
 */
public class FanOutTest
{
    /**
     * Test that we can the correct result for MultipleClasses.java
     */
    @Test
    public void test_simple_case()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_out\\FanOut.java", true);
        FanOutAnalysis tester = new FanOutAnalysis();
        tester.performAnalysis(launcher);
        System.out.println(tester);
    }
}
