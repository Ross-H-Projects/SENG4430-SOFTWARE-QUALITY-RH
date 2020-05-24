package group3;


import group3.metric_analysis.conditional_nesting.ConditionalNestingTracker;
import group3.metric_analysis.fan_out.FanOutAnalysis;
import group3.metric_analysis.fan_out.FanOutTracker;
import org.junit.Test;
import spoon.Launcher;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for Depth of Inheritance Tree Algorithm.
 */
public class CommentTest
{
    /**
     * Test that we can the correct result for ConditionalNesting.java
     */
    @Test
    public void test_empty_class()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\ConditionalNestingTestEmptyClass.java", true);
        ConditionalNestingTracker tester = new ConditionalNestingTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{ConditionalTest={ConditionalTest()=0}}", res);
    }
    @Test
    public void test_single_method_class()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\ConditionalNestingTestSingleMethodClass.java", true);
        ConditionalNestingTracker tester = new ConditionalNestingTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{ConditionalTest={test1()=3, main(java.lang.String[])=0, ConditionalTest()=0}}", res);
    }
    @Test
    public void test_multiple_method_class()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\ConditionalNestingTestMultipleMethodClass.java", true);
        ConditionalNestingTracker tester = new ConditionalNestingTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{ConditionalTest={test1()=3, test2()=4, main(java.lang.String[])=0, ConditionalTest()=0}}", res);
    }
    @Test
    public void test_nested_ifs()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\conditional_nesting\\ConditionalNestingTestIfStatementTypes.java", true);
        ConditionalNestingTracker tester = new ConditionalNestingTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{ConditionalTest={test7()=2, test8()=1, test5()=2, test6()=2, test3()=1, test4()=2, test1()=1, test10()=1, test2()=1, main(java.lang.String[])=0, test9()=1, ConditionalTest()=0}}", res);
    }
}
