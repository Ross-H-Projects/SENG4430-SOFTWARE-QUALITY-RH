package group3;


import static org.junit.Assert.assertEquals;


import group3.metric_analysis.depth_inheritance_tree.DepthInheritanceTreeAnalysis;
import group3.metric_analysis.depth_inheritance_tree.DepthInheritanceTreeTracker;
import org.junit.Test;
import spoon.Launcher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;

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
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/DepthInheritance/MultipleClasses.java", false);
        DepthInheritanceTreeAnalysis tester = new DepthInheritanceTreeAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("Depth of Inheritance for MultipleClasses.java must be 3", 3, tester.getMaxDepth());
    }

    /**
     * Test that we can the correct result for the class files in DepthInheritance_2 directory
     */
    @Test
    public void test2()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\test_code_samples\\DepthInheritance_2", false);
        DepthInheritanceTreeAnalysis tester = new DepthInheritanceTreeAnalysis();
        tester.performAnalysis(launcher);
        assertEquals("Depth of Inheritance for code_samples\\test_code_samples\\DepthInheritance_2 directory must be 2", 2, tester.getMaxDepth());
    }

    /**
     * Test the 'code from the wild' code sample
     */
    @Test
    public void testWild()
    {
        Launcher launcher = Utilities.importCodeSample("code_samples\\SENG3340_Previous_Semester_Example", false);

        DepthInheritanceTreeTracker tester = new DepthInheritanceTreeTracker(new String[0]);
        tester.run(launcher);

        String resultJson = tester.toJson();

        String expectedJson = "{'Depth Of Inheritance': \n" +
                "\t{\n" +
                "\t\t'score': '1',\n" +
                "\t\t'chains': [\n" +
                "\t\t\t[ 'ConsoleInterface$5', 'ConsoleInterface$Command' ],\n" +
                "\t\t\t[ 'modules.AverageLengthOfIdentifier$CollectionMode' ],\n" +
                "\t\t\t[ 'ConsoleInterface$4', 'ConsoleInterface$Command' ],\n" +
                "\t\t\t[ 'ConsoleInterface$3', 'ConsoleInterface$Command' ],\n" +
                "\t\t\t[ 'ConsoleInterface$2', 'ConsoleInterface$Command' ],\n" +
                "\t\t\t[ 'modules.FanOut' ],\n" +
                "\t\t\t[ 'ConsoleInterface$8', 'ConsoleInterface$Command' ],\n" +
                "\t\t\t[ 'ConsoleInterface$7', 'ConsoleInterface$Command' ],\n" +
                "\t\t\t[ 'ConsoleInterface$6', 'ConsoleInterface$Command' ],\n" +
                "\t\t\t[ 'ModuleManager' ],\n" +
                "\t\t\t[ 'flowgraph.FlowGraph' ],\n" +
                "\t\t\t[ 'flowgraph.FlowGraph$FlowGraphNode' ],\n" +
                "\t\t\t[ 'utils.Adjustment' ],\n" +
                "\t\t\t[ 'modules.NumberOfComments$MethodNameCollector' ],\n" +
                "\t\t\t[ 'ConsoleInterface' ],\n" +
                "\t\t\t[ 'ConsoleInterface$1', 'ConsoleInterface$Command' ],\n" +
                "\t\t\t[ 'modules.CyclomaticComplexity', 'modules.FlowGraphNumberExtractor' ],\n" +
                "\t\t\t[ 'modules.FlowGraphNumberExtractor$CodeBlock' ],\n" +
                "\t\t\t[ 'modules.FlowGraphNumberExtractor$ClassInfo' ],\n" +
                "\t\t\t[ 'modules.helpers.TableUtil' ],\n" +
                "\t\t\t[ 'modules.NumberOfPaths', 'modules.FlowGraphNumberExtractor' ],\n" +
                "\t\t\t[ 'modules.FanIn' ],\n" +
                "\t\t\t[ 'modules.FlowGraphNumberExtractor$Method', 'modules.FlowGraphNumberExtractor$CodeBlock' ],\n" +
                "\t\t\t[ 'modules.LengthOfConditionalBlocks' ],\n" +
                "\t\t\t[ 'modules.UnmeetableCode' ],\n" +
                "\t\t\t[ 'ConsoleInterface$Command' ],\n" +
                "\t\t\t[ 'modules.AverageLengthOfIdentifier$IdentifierVisitorArg' ],\n" +
                "\t\t\t[ 'modules.FogIndex' ],\n" +
                "\t\t\t[ 'utils.Util' ],\n" +
                "\t\t\t[ 'SENG4430' ],\n" +
                "\t\t\t[ 'modules.helpers.LengthHelper' ],\n" +
                "\t\t\t[ 'modules.NumberOfComments' ],\n" +
                "\t\t\t[ 'modules.AverageLengthOfIdentifier$IdentifierVisitor' ],\n" +
                "\t\t\t[ 'utils.ExtendedProperties' ],\n" +
                "\t\t\t[ 'flowgraph.AbstractFlowGraphBuilder' ],\n" +
                "\t\t\t[ 'modules.LengthOfCode' ],\n" +
                "\t\t\t[ 'flowgraph.CyclicFlowGraphBuilder', 'flowgraph.AbstractFlowGraphBuilder' ],\n" +
                "\t\t\t[ 'modules.helpers.FileReport' ],\n" +
                "\t\t\t[ 'modules.LocalVariables' ],\n" +
                "\t\t\t[ 'modules.LocalVariables$MethodVariablesCollector' ],\n" +
                "\t\t\t[ 'modules.helpers.Analysis' ],\n" +
                "\t\t\t[ 'utils.Logger' ],\n" +
                "\t\t\t[ 'flowgraph.AcyclicFlowGraphBuilder', 'flowgraph.AbstractFlowGraphBuilder' ],\n" +
                "\t\t\t[ 'modules.LocalVariables$MethodNameCollector' ],\n" +
                "\t\t\t[ 'modules.FlowGraphNumberExtractor' ],\n" +
                "\t\t\t[ 'modules.helpers.Warning' ],\n" +
                "\t\t\t[ 'modules.AverageLengthOfIdentifier' ],\n" +
                "\t\t\t[ 'modules.StringMatchFanIn' ],\n" +
                "\t\t\t[ 'modules.FlowGraphNumberExtractor$Constructor', 'modules.FlowGraphNumberExtractor$CodeBlock' ],\n" +
                "\t\t\t[ 'modules.UnmeetableCode$ConditionVisitor' ]\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";

        assertEquals("Depth of Inheritance for code_samples\\SENG3340_Previous_Semester_Example directory must be:\n", expectedJson, resultJson);
    }

    @Test
    public void testEntireProgramDepthInheritance1() throws IOException {

        String[] testArgs = {
                "code_samples\\test_code_samples\\DepthInheritance_2"
                ,"-m"
                ,"inheritance_depth"
        };

        PrintStream old = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);
        App.main(testArgs);
        System.out.flush();
        System.setOut(old);
        String s = new String(baos.toByteArray(), Charset.defaultCharset());

        String expectedJson = "{'Depth Of Inheritance': \n" +
                "\t{\n" +
                "\t\t'score': '2',\n" +
                "\t\t'chains': [\n" +
                "\t\t\t[ 'A' ],\n" +
                "\t\t\t[ 'B', 'A' ],\n" +
                "\t\t\t[ 'C', 'B', 'A' ]\n" +
                "\t\t]\n" +
                "\t}\n" +
                "}";

        assertEquals("System Test: App should return: \n", expectedJson, s.trim());
    }
}
