package group3;

import group3.metric_analysis.coupling.CouplingAnalysis;
import org.junit.Test;
import spoon.Launcher;

import static org.junit.Assert.assertEquals;

public class CouplingTest {

    @Test
    public void test1(){
        Launcher launcher = Utilities.importCodeSample("code_samples\\Coupling_Example\\A3", false);
        CouplingAnalysis tester = new CouplingAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("Coupling for couplingTest.java must be 9 (7 implicit constructor calls, 2 explicit calls through object reference", 9, tester.getCouplingTotal());
    }

}
