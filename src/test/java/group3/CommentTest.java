package group3;


import group3.metric_analysis.comments_counts.CommentsCountTracker;
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
    @Test
    public void test_empty_class()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestEmptyClass.java", true);
        CommentsCountTracker tester = new CommentsCountTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{Comments Analysis where ratio <= 40% :{{Class Comments Analysis: {\"ConditionalTest\":[{\"Inline Comment Ratio:\":0.0},{\"Inline Comments Count:\":0.0}]}}, {Method Comments Analysis: {\"ConditionalTest\":{}}}}}", res);
    }
    @Test
    public void test_empty_main()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestEmptyClass.java", true);
        CommentsCountTracker tester = new CommentsCountTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{Comments Analysis where ratio <= 40% :{{Class Comments Analysis: {\"ConditionalTest\":[{\"Inline Comment Ratio:\":0.0},{\"Inline Comments Count:\":0.0}]}}, {Method Comments Analysis: {\"ConditionalTest\":{}}}}}", res);
    }
    @Test
    public void test_on_if()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestEmptyClass.java", true);
        CommentsCountTracker tester = new CommentsCountTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{Comments Analysis where ratio <= 40% :{{Class Comments Analysis: {\"ConditionalTest\":[{\"Inline Comment Ratio:\":0.0},{\"Inline Comments Count:\":0.0}]}}, {Method Comments Analysis: {\"ConditionalTest\":{}}}}}", res);
    }
    @Test
    public void test_non_empty_main()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestEmptyClass.java", true);
        CommentsCountTracker tester = new CommentsCountTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{Comments Analysis where ratio <= 40% :{{Class Comments Analysis: {\"ConditionalTest\":[{\"Inline Comment Ratio:\":0.0},{\"Inline Comments Count:\":0.0}]}}, {Method Comments Analysis: {\"ConditionalTest\":{}}}}}", res);
    }
    @Test
    public void test_non_empty_main_multiline()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestEmptyClass.java", true);
        CommentsCountTracker tester = new CommentsCountTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{Comments Analysis where ratio <= 40% :{{Class Comments Analysis: {\"ConditionalTest\":[{\"Inline Comment Ratio:\":0.0},{\"Inline Comments Count:\":0.0}]}}, {Method Comments Analysis: {\"ConditionalTest\":{}}}}}", res);
    }
}
