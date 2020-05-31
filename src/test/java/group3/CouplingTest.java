package group3;

import group3.metric_analysis.coupling.CouplingAnalysis;
import group3.metric_analysis.coupling.CouplingTracker;
import org.junit.Test;
import spoon.Launcher;

import static org.junit.Assert.assertEquals;

public class CouplingTest {

    @Test
    public void test1(){
        Launcher launcher = Utilities.importCodeSample("code_samples/Coupling_Example/c2", false);
        CouplingAnalysis tester = new CouplingAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("Coupling for couplingTest.java must be 15", 15, (int) tester.getCouplingTotal());

    }



}
