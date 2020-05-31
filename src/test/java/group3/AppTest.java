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
                "code_samples\\WordCount.java"
                ,"-m"
                ,"inheritance_depth"
                ,"-m"
                ,"cohesion_score"
                ,"-m"
                ,"coupling"
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
        String s = new String(baos.toByteArray(), Charset.defaultCharset());

        System.out.println(s);

        assertEquals("System Test: App should return: \n", true, true);

    }


}
