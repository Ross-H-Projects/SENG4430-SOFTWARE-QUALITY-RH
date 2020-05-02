package group3;


import static org.junit.Assert.assertEquals;


import group3.metric_analysis.depth_inheritance_tree.DepthInheritanceTreeAnalysis;
import org.junit.Test;
import spoon.Launcher;

/**
 * Unit tests for Depth of Inheritance Tree Algorithm.
 */
public class DepthInheritanceTreeAnalysisTest
{
    /**
     * Test that we can the correct result for MultipleClasses.java
     */
    @Test
    public void test1()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\DepthInheritance\\MultipleClasses.java");
        DepthInheritanceTreeAnalysis tester = new DepthInheritanceTreeAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("Depth of Inheritance for MultipleClasses.java must be 3", 3, tester.getMaxDepth());
    }

    /**
     * Test that we can the correct result for the class files in
     */
    @Test
    public void test2()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\DepthInheritance_2");
        DepthInheritanceTreeAnalysis tester = new DepthInheritanceTreeAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("Depth of Inheritance for DepthInheritance_2 directory must be 2", 3, tester.getMaxDepth());
    }
}
