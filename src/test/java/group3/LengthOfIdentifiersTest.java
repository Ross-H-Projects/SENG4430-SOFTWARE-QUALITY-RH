package group3;

import group3.metric_analysis.length_of_identifiers.LengthOfIdentifiersAnalysis;
import spoon.Launcher;
import org.junit.Test;


public class LengthOfIdentifiersTest {

    public void test1(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/LengthOfIdentifiers/LengthOfIdentifiersEx.java", false);
        LengthOfIdentifiersAnalysis lengthOfIdentifiersAnalysis = new LengthOfIdentifiersAnalysis();
        lengthOfIdentifiersAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = lengthOfIdentifiersAnalysis.getClassLengthOfIdentifiersScores().get("LengthOfIdentifiersEx");
        //assertEquals("The average length of identifiers for LengthOfIdentifiersEx.java is 7", 7, resultOfAnalysis);
    }
    public void test2(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/LengthOfIdentifiers", false);
        LengthOfIdentifiersAnalysis lengthOfIdentifiersAnalysis = new LengthOfIdentifiersAnalysis();
        lengthOfIdentifiersAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = lengthOfIdentifiersAnalysis.getClassLengthOfIdentifiersScores().get("LengthOfIdentifiersEx");
        double resultOfAnalysis2 = lengthOfIdentifiersAnalysis.getClassLengthOfIdentifiersScores().get("LoIEx2");
        //assertEquals("The average length of identifiers for LengthOfIdentifiersEx.java is 7", 7, resultOfAnalysis);
        //assertEquals("The average length of identifiers for LoIEx2.java is 4", 4, resultOfAnalysis2);
    }
}
