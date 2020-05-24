package group3;

import group3.metric_analysis.fog_index.FogIndexAnalysis;
import group3.metric_analysis.fog_index.FogIndexTracker;
import org.junit.Test;
import spoon.Launcher;
import static junit.framework.TestCase.assertEquals;

public class FogIndexTest {

    @Test
    public void testSimpleCase(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/FogIndex/Shakespeare.java", true);
        FogIndexAnalysis fogIndexAnalysis = new FogIndexAnalysis();
        fogIndexAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = fogIndexAnalysis.getFogForTest().get("Shakespeare").get("methodA");
        assertEquals("The fog index for this method is 1.6", 1.6, resultOfAnalysis);
    }

    @Test
    public void testComplexMultipleComments(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/FogIndex/Shakespeare.java", true);
        FogIndexAnalysis fogIndexAnalysis = new FogIndexAnalysis();
        fogIndexAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = fogIndexAnalysis.getFogForTest().get("Shakespeare").get("methodB");
        assertEquals("The fog index for this method is 18.2", 18.2, resultOfAnalysis);
    }

    @Test
    public void testDocComment(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/FogIndex/Shakespeare.java", true);
        FogIndexAnalysis fogIndexAnalysis = new FogIndexAnalysis();
        fogIndexAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = fogIndexAnalysis.getFogForTest().get("Shakespeare").get("methodC");
        assertEquals("The fog index for this method is 15.73", 15.733333333333333, resultOfAnalysis);
    }

    @Test
    public void testDocMixedRegularComments(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/FogIndex/Shakespeare.java", true);
        FogIndexAnalysis fogIndexAnalysis = new FogIndexAnalysis();
        fogIndexAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = fogIndexAnalysis.getFogForTest().get("Shakespeare").get("methodD");
        assertEquals("The fog index for this method is 10.69", 10.68888888888889, resultOfAnalysis);
    }

    @Test
    public void testMultipleClasses(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/FogIndex", true);
        FogIndexAnalysis fogIndexAnalysis = new FogIndexAnalysis();
        fogIndexAnalysis.performAnalysis(launcher);
        double resultOfAnalysisShakespeare = fogIndexAnalysis.getFogForTest().get("Shakespeare").get("methodD");
        double resultOfAnalysisHamlet = fogIndexAnalysis.getFogForTest().get("Hamlet").get("methodA");
        assertEquals("The fog index for this method is 10.69", 10.68888888888889, resultOfAnalysisShakespeare);
        assertEquals("The fog index for this method is 15.73", 15.733333333333333, resultOfAnalysisHamlet);
    }

}
