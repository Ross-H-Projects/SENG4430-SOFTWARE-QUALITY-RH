package group3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import group3.metric_analysis.depth_inheritance_tree.DepthInheritanceTreeAnalysis;
import org.apache.maven.shared.invoker.PrintStreamLogger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import spoon.Launcher;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Unit test for simple App.
 */
public class AppTest 
{


    /**
     * Test the entire program with certain arguements
     */
    @Test
    public void systemTest()
    {
        String[] testArgs = {
                "src\\main"
                ,"-m"
                ,"inheritance_depth"
                ,"-m"
                ,"cohesion_score"
                ,"-m"
                ,"coupling"
                ,"-m"
                ,"fan_out"
                ,"-m"
                ,"fan_in"
                ,"-m"
                ,"halstead_complexity"
        };

        PrintStream old = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(baos);
        System.setOut(out);
        App.main(testArgs);
        System.out.flush();
        System.setOut(old);
        String programOutput = new String(baos.toByteArray(), Charset.defaultCharset());
        programOutput = programOutput.replaceAll("\\s+","");

        // read in system test expected  output from file
        String expectedProgramOutput = "";
        try
        {
            expectedProgramOutput = new String ( Files.readAllBytes(Paths.get("src/test/java/group3/SystemTestOutput.txt")));
            expectedProgramOutput = expectedProgramOutput.replaceAll("\\s+","");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        assertEquals("System Test: App should return: \n", expectedProgramOutput, programOutput);

    }


}
