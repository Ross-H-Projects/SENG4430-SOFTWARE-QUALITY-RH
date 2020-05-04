package group3;


import group3.metric_analysis.fan_out.FanOutAnalysis;
import group3.metric_analysis.fan_out.FanOutTracker;
import org.junit.Test;
import spoon.Launcher;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for Depth of Inheritance Tree Algorithm.
 */
public class FanOutTest
{
    /**
     * Test that we can the correct result for FanOut.java
     */
    @Test
    public void test_simple_case()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_out\\FanOut.java", true);
        FanOutTracker tester = new FanOutTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{FanOut1={test()=1, FanOut1()=0}}", res);
    }

    /**
     * Test that commented invocations do not count as a real invocation
     */
    @Test
    public void test_with_commented_invocations() {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_out\\FanOutCommented.java", true);
        FanOutTracker tester = new FanOutTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{FanOutCommented={FanOutCommented()=0, test()=1}}", res);
    }
}
