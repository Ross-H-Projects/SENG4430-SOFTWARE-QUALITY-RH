package group3;

import group3.metric_analysis.fog_index.FogIndexAnalysis;
import org.junit.Test;
import spoon.Launcher;
import static junit.framework.TestCase.assertEquals;

/**
 * @author DanielSands
 * Unit tests for fog index metric
 */
public class FogIndexTest {

    /**
     * Tests very simple inline comment
     */
    @Test
    public void testSimpleInline(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/FogIndex/Shakespeare.java", true);
        FogIndexAnalysis fogIndexAnalysis = new FogIndexAnalysis();
        fogIndexAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = fogIndexAnalysis.getFogIndex().get("Shakespeare").get("methodA");
        assertEquals("The fog index for this method is 1.6", 1.6, resultOfAnalysis);
    }

    /**
     * Tests method with multiple comments and complex words
     */
    @Test
    public void testComplexInlineMultipleComments(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/FogIndex/Shakespeare.java", true);
        FogIndexAnalysis fogIndexAnalysis = new FogIndexAnalysis();
        fogIndexAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = fogIndexAnalysis.getFogIndex().get("Shakespeare").get("methodB");
        assertEquals("The fog index for this method is 18.2", 18.2, resultOfAnalysis);
    }

    /**
     * Tests method with doc comment
     */
    @Test
    public void testDocComment(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/FogIndex/Shakespeare.java", true);
        FogIndexAnalysis fogIndexAnalysis = new FogIndexAnalysis();
        fogIndexAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = fogIndexAnalysis.getFogIndex().get("Shakespeare").get("methodC");
        assertEquals("The fog index for this method is 15.73", 15.733333333333333, resultOfAnalysis);
    }

    /**
     * Tests method with both doc comment and inline comments
     */
    @Test
    public void testDocMixedRegularComments(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/FogIndex/Shakespeare.java", true);
        FogIndexAnalysis fogIndexAnalysis = new FogIndexAnalysis();
        fogIndexAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = fogIndexAnalysis.getFogIndex().get("Shakespeare").get("methodD");
        assertEquals("The fog index for this method is 10.69", 10.68888888888889, resultOfAnalysis);
    }

    /**
     * Tests fog index when run on multiple classes
     */
    @Test
    public void testMultipleClasses(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/FogIndex", true);
        FogIndexAnalysis fogIndexAnalysis = new FogIndexAnalysis();
        fogIndexAnalysis.performAnalysis(launcher);
        double resultOfAnalysisShakespeare = fogIndexAnalysis.getFogIndex().get("Shakespeare").get("methodD");
        double resultOfAnalysisHamlet = fogIndexAnalysis.getFogIndex().get("Hamlet").get("methodA");
        assertEquals("The fog index for this method is 10.69", 10.68888888888889, resultOfAnalysisShakespeare);
        assertEquals("The fog index for this method is 15.73", 15.733333333333333, resultOfAnalysisHamlet);
    }

}
