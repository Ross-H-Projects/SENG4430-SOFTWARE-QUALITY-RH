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
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    /**
     * Test that we can the correct result for MultipleClasses.java
     */
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

        String shouldReturn = "class group3.metric_analysis.depth_inheritance_tree.DepthInheritanceTreeTracker : 2";

        assertEquals("App should return:\n" + shouldReturn + "\n", shouldReturn, s.strip());
    }
}
