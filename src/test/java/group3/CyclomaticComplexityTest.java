package group3;


import group3.metric_analysis.cyclomatic_complexity.CyclomaticTracker;
import org.junit.Test;
import spoon.Launcher;
import static org.junit.Assert.assertEquals;

public class CyclomaticComplexityTest
{
    //
    @Test
    public void test_sample()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\cyclomatic_complexity\\cyclomaticTest.java", true);
        CyclomaticTracker tester = new CyclomaticTracker(new String[] {});
        tester.run(launcher);
        String res = tester.toJson();
        assertEquals("{code_samples.test_code_samples.cyclomatic_complexity.cyclomaticTest=9}", res);
    }
}
