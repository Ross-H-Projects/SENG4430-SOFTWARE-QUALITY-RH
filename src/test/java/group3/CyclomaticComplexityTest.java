package group3;


import group3.metric_analysis.cyclomatic_complexity.CyclomaticTracker;
import org.junit.Test;
import spoon.Launcher;
import static org.junit.Assert.assertEquals;

public class CyclomaticComplexityTest
{
    // Test includes each of the basic components that cyclomatic complexity looks for.
    @Test
    public void test_all()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\cyclomatic_complexity\\cyclomaticTest.java", true);
        CyclomaticTracker tester = new CyclomaticTracker(new String[] {});
        tester.run(launcher);
        int res = tester.fetchCMC("cyclomaticTest.main");
        assertEquals(10, res);
    }

    //Test includes no decision points, should reach base value of 1
    @Test
    public void test_baseline()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\cyclomatic_complexity\\cyclomaticTest1.java", true);
        CyclomaticTracker tester = new CyclomaticTracker(new String[] {});
        tester.run(launcher);
        int res = tester.fetchCMC("cyclomaticTest1.main");
        assertEquals(1, res);
    }

    //Test includes only "if" decision points
    @Test
    public void test_if()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\cyclomatic_complexity\\cyclomaticTest2.java", true);
        CyclomaticTracker tester = new CyclomaticTracker(new String[] {});
        tester.run(launcher);
        int res = tester.fetchCMC("cyclomaticTest2.main");
                assertEquals(3, res);
    }

    //Test includes only "for" decision points
    @Test
    public void test_for()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\cyclomatic_complexity\\cyclomaticTest3.java", true);
        CyclomaticTracker tester = new CyclomaticTracker(new String[] {});
        tester.run(launcher);
        int res = tester.fetchCMC("cyclomaticTest3.main");
                assertEquals(2, res);
    }

    //Test includes only "while" decision points
    @Test
    public void test_while()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\cyclomatic_complexity\\cyclomaticTest4.java", true);
        CyclomaticTracker tester = new CyclomaticTracker(new String[] {});
        tester.run(launcher);
        int res = tester.fetchCMC("cyclomaticTest4.main");
                assertEquals(2, res);
    }

    }//Test includes only "if" and "else" decision points
    @Test
    public void test_if_else()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\cyclomatic_complexity\\cyclomaticTest5.java", true);
        CyclomaticTracker tester = new CyclomaticTracker(new String[] {});
        tester.run(launcher);
        int res = tester.fetchCMC("cyclomaticTest5.main");
                assertEquals(2, res);
    }

    //Test includes only "switch case" decision points
    @Test
    public void test_switch_case()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\cyclomatic_complexity\\cyclomaticTest6.java", true);
        CyclomaticTracker tester = new CyclomaticTracker(new String[] {});
        tester.run(launcher);
        int res = tester.fetchCMC("cyclomaticTest6.main");
                assertEquals(4, res);
    }

    //Test includes a single "if" decision point with a decision operator (&& or ||) in it
    @Test
    public void test_if_condition()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\cyclomatic_complexity\\cyclomaticTest7.java", true);
        CyclomaticTracker tester = new CyclomaticTracker(new String[] {});
        tester.run(launcher);
        int res = tester.fetchCMC("cyclomaticTest7.main");
                assertEquals(3, res);
    }
}
