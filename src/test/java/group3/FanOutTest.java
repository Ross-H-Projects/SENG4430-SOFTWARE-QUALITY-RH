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
     * Test that we can the correct result for FanOut.java in method mode
     */
    @Test
    public void test_method_mode()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_out\\FanOut.java", true);
        FanOutTracker tester = new FanOutTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assert res.contains("\"fanOut1_1()\":2");
        assert res.contains("\"fanOut2_2()\":1");
        assert res.contains("\"fanOut2_1()\":1");
    }

    /**
     * Test that we can the correct result for FanOut.java in method mode
     */
    @Test
    public void test_module_mode()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\fan_out\\FanOut.java", true);
        FanOutTracker tester = new FanOutTracker(new String[] {"-module"});
        tester.run(launcher);
        String res = tester.toJson();
        assert res.contains("\"FanOut1\":2");
        assert res.contains("\"FanOut2\":1");
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
        assert res.contains("\"FanOutCommented()\":0");
        assert res.contains("\"test()\":1");
    }
}
