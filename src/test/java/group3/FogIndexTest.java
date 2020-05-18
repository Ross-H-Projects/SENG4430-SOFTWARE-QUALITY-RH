package group3;

import group3.metric_analysis.fog_index.FogIndexAnalysis;
import group3.metric_analysis.fog_index.FogIndexTracker;
import org.junit.Test;
import spoon.Launcher;
import static junit.framework.TestCase.assertEquals;

public class FogIndexTest {

    @Test
    public void test1(){
        Launcher launcher = Utilities.importCodeSample("src/main/java/group3/metric_analysis/fog_index", true);
        FogIndexAnalysis fogIndexAnalysis = new FogIndexAnalysis();
        fogIndexAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = fogIndexAnalysis.getFogForTest().get("FogIndexAnalysis").get("calculateFogIndex");
        System.out.println(" TEST GIVES: " + resultOfAnalysis);
        //assertEquals(); TODO: Assert equals
    }
}
