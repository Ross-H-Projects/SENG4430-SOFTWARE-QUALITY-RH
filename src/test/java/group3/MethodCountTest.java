package group3;


import group3.metric_analysis.cyclomatic_complexity.CyclomaticTracker;
import group3.metric_analysis.method_count.MethodCountTracker;
import org.junit.Test;
import spoon.Launcher;
import static org.junit.Assert.assertEquals;

public class MethodCountTest
{
    // Test includes each of the basic components that cyclomatic complexity looks for.
    @Test
    public void test_zeromethods()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\method_count\\method_CountTest.java", true);
        MethodCountTracker tester = new MethodCountTracker(new String[] {});
        tester.run(launcher);
        int res = tester.getCount("methodCountTest");
        assertEquals(0, res);
    }

    //Test includes no decision points, should reach base value of 1
    @Test
    public void test_nonzeromethods()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\method_count\\method_CountTest1.java", true);
        MethodCountTracker tester = new MethodCountTracker(new String[] {});
        tester.run(launcher);
        int res = tester.getCount("methodCountTest1");
        assertEquals(3, res);
    }
}
