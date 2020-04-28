package group3;


import static org.junit.Assert.assertEquals;


import org.junit.Test;

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
        DepthInheritanceTreeAnalysis tester = new DepthInheritanceTreeAnalysis();
        DepthInheritanceTreeReturn testerResult =  (DepthInheritanceTreeReturn) tester.performAnalysis("code_samples\\test_code_samples\\DepthInheritance\\MultipleClasses.java");
        assertEquals("Depth of Inheritance for MultipleClasses.java must be 3", 3, testerResult.getMaxDepth());
    }

    /**
     * Test that we can the correct result for the class files in
     */
    @Test
    public void test2()
    {
        DepthInheritanceTreeAnalysis tester = new DepthInheritanceTreeAnalysis();
        DepthInheritanceTreeReturn testerResult =  (DepthInheritanceTreeReturn) tester.performAnalysis("code_samples\\test_code_samples\\DepthInheritance_2");
        assertEquals("Depth of Inheritance for DepthInheritance_2 directory must be 2", 3, testerResult.getMaxDepth());
    }
}
