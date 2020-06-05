package group3;


import group3.metric_analysis.comments_counts.CommentsCountAnalysis;
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
        CommentsCountAnalysis analysis = new CommentsCountAnalysis(true, false, false, 40);
        analysis.performAnalysis(launcher);
        assertEquals(analysis.getClassCommentAnalysis().toString(), "{ConditionalTest=[{Comment Ratio:=0.0}, {Comments Count:=0.0}]}");
        assertEquals(analysis.getMethodCommentAnalysis().toString(), "{ConditionalTest={}}");
    }
    @Test
    public void test_empty_main()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestEmptyMain.java", true);
        CommentsCountAnalysis analysis = new CommentsCountAnalysis(true, false, false, 40);
        analysis.performAnalysis(launcher);
        assertEquals(analysis.getClassCommentAnalysis().toString(), "{ConditionalTest=[{Comment Ratio:=0.0}, {Comments Count:=0.0}]}");
        assertEquals(analysis.getMethodCommentAnalysis().toString(), "{ConditionalTest={main=[{Comment Ratio:=0.0}, {Comments Count:=0.0}]}}");
    }
    @Test
    public void test_on_non_empty_main_1()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestNonEmptyMain.java", true);
        CommentsCountAnalysis analysis = new CommentsCountAnalysis(true, false, false, 40);
        analysis.performAnalysis(launcher);
        assertEquals(analysis.getClassCommentAnalysis().toString(), "{ConditionalTest=[{Comment Ratio:=33.33333333333333}, {Comments Count:=2.0}]}");
        assertEquals(analysis.getMethodCommentAnalysis().toString(), "{ConditionalTest={main=[]}}");
    }
    @Test
    public void test_on_non_empty_main_2()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestNonEmptyMainLarge.java", true);
        CommentsCountAnalysis analysis = new CommentsCountAnalysis(true, false, false, 40);
        analysis.performAnalysis(launcher);
        assertEquals(analysis.getClassCommentAnalysis().toString(), "{ConditionalTest=[{Comment Ratio:=18.181818181818183}, {Comments Count:=2.0}]}");
        assertEquals(analysis.getMethodCommentAnalysis().toString(), "{ConditionalTest={main=[{Comment Ratio:=25.0}, {Comments Count:=2.0}]}}");
    }
    @Test
    public void test_on_non_empty_main_multiline_docstring()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestNonEmptyMainMultilineDocstring.java", true);
        CommentsCountAnalysis analysis = new CommentsCountAnalysis(true, false, false, 40);
        analysis.performAnalysis(launcher);
        assertEquals(analysis.getClassCommentAnalysis().toString(), "{ConditionalTest=[{Comment Ratio:=14.285714285714285}, {Comments Count:=1.0}]}");
        assertEquals(analysis.getMethodCommentAnalysis().toString(), "{ConditionalTest={main=[]}}");
    }
    @Test
    public void test_on_non_empty_main_multiline_inline()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestNonEmptyMainMultilineInline.java", true);
        CommentsCountAnalysis analysis = new CommentsCountAnalysis(true, false, false, 40);
        analysis.performAnalysis(launcher);
        assertEquals(analysis.getClassCommentAnalysis().toString(), "{ConditionalTest=[{Comment Ratio:=14.285714285714285}, {Comments Count:=1.0}]}");
        assertEquals(analysis.getMethodCommentAnalysis().toString(), "{ConditionalTest={main=[{Comment Ratio:=20.0}, {Comments Count:=1.0}]}}");
    }
    @Test
    public void test_on_non_empty_method()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestNonEmptyMethod.java", true);
        CommentsCountAnalysis analysis = new CommentsCountAnalysis(true, false, false, 40);
        analysis.performAnalysis(launcher);
        assertEquals(analysis.getClassCommentAnalysis().toString(), "{FanIn1=[{Comment Ratio:=25.0}, {Comments Count:=2.0}]}");
        assertEquals(analysis.getMethodCommentAnalysis().toString(), "{FanIn1={FanIn1_1=[{Comment Ratio:=33.33333333333333}, {Comments Count:=2.0}]}}");
    }
    @Test
    public void test_on_non_empty_methods()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestNonEmptyMethods.java", true);
        CommentsCountAnalysis analysis = new CommentsCountAnalysis(true, false, false, 40);
        analysis.performAnalysis(launcher);
        assertEquals(analysis.getClassCommentAnalysis().toString(), "{ConditionalTest=[{Comment Ratio:=31.25}, {Comments Count:=5.0}]}");
        assertEquals(analysis.getMethodCommentAnalysis().toString(), "{ConditionalTest={}}");
    }
    @Test
    public void test_comment_ratio_above_40()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\comments\\CommentsTestCommentRatioAbove40.java", true);
        CommentsCountAnalysis analysis = new CommentsCountAnalysis(true, false, false, 40);
        analysis.performAnalysis(launcher);
        assertEquals(analysis.getClassCommentAnalysis().toString(), "{ConditionalTest=[]}");
        assertEquals(analysis.getMethodCommentAnalysis().toString(), "{ConditionalTest={}}");
    }
}
