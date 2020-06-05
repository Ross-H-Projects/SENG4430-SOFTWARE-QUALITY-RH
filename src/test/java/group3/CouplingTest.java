package group3;

import group3.metric_analysis.coupling.CouplingAnalysis;
import group3.metric_analysis.coupling.CouplingTracker;

import org.junit.Test;
import spoon.Launcher;

import static org.junit.Assert.assertEquals;
/**
 * Unit tests for Fan Out
 */
public class CouplingTest {
    /**
     * Test that we can get the correct result for Coupling.java
     */
    @Test
    public void test1(){
        Launcher launcher = Utilities.importCodeSample("code_samples/Coupling_Example/c2", false);
        CouplingAnalysis tester = new CouplingAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("Coupling for couplingTest.java must be 15", 15, (int) tester.getCouplingTotal());

    }

    /**
     * Test the json output for the program in c1 directory
     */
    @Test
    public void test2(){
        Launcher launcher = Utilities.importCodeSample("code_samples/Coupling_Example/c1", false);
        CouplingAnalysis tester = new CouplingAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("Coupling for c1 must be 64", 64, (int) tester.getCouplingTotal());

    }

    /**
     * Test the json output for the program in c1 directory
     */
    @Test
    public void testJson(){
        Launcher launcher = Utilities.importCodeSample("code_samples/Coupling_Example/c1", false);

        CouplingTracker tester = new CouplingTracker(new String[0]);
        tester.run(launcher);

        String resultJson = tester.toJson();

        String expectedJson =
                "{'Coupling': {\n" +
                "\t'Coupling Total': 64,\n"+
                "\t'Weighted Graph': {\n" +
                "\t\t'A3': {\n" +
                "\t\t\t'Page': 2,\n" +
                "\t\t\t'Process': 2,\n" +
                "\t\t\t'MemoryManagementUnit': 0,\n" +
                "\t\t\t'CPU': 6\n" +
                "\t\t},\n" +
                "\t\t'Page': {\n" +
                "\t\t\t'A3': 0,\n" +
                "\t\t\t'Process': 0,\n" +
                "\t\t\t'MemoryManagementUnit': 0,\n" +
                "\t\t\t'CPU': 0\n" +
                "\t\t},\n" +
                "\t\t'Process': {\n" +
                "\t\t\t'A3': 0,\n" +
                "\t\t\t'Page': 2,\n" +
                "\t\t\t'MemoryManagementUnit': 3,\n" +
                "\t\t\t'CPU': 0\n" +
                "\t\t},\n" +
                "\t\t'MemoryManagementUnit': {\n" +
                "\t\t\t'A3': 0,\n" +
                "\t\t\t'Page': 10,\n" +
                "\t\t\t'Process': 0,\n" +
                "\t\t\t'CPU': 0\n" +
                "\t\t},\n" +
                "\t\t'CPU': {\n" +
                "\t\t\t'A3': 0,\n" +
                "\t\t\t'Page': 3,\n" +
                "\t\t\t'Process': 32,\n" +
                "\t\t\t'MemoryManagementUnit': 4\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";

        assertEquals("Coupling for code_samples/Coupling_Example/c1 directory must be:\n", expectedJson, resultJson);

    }








}
