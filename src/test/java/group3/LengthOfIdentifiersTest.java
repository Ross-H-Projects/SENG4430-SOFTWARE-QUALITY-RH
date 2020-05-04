package group3;

import group3.metric_analysis.length_of_identifiers.LengthOfIdentifiersAnalysis;
import spoon.Launcher;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Unit tests for Length of identifiers metric
 * @author DanielSands
 */
public class LengthOfIdentifiersTest {

    /**
     * Tests that we get the correct result for LengthOfIdentifiersEx.java
     */
    @Test
    public void testSingleClass(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/LengthOfIdentifiers/LengthOfIdentifiersEx.java", false);
        LengthOfIdentifiersAnalysis lengthOfIdentifiersAnalysis = new LengthOfIdentifiersAnalysis();
        lengthOfIdentifiersAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = lengthOfIdentifiersAnalysis.getClassLengthOfIdentifiersScores().get("LengthOfIdentifiersEx");
        assertEquals("The average length of identifiers for LengthOfIdentifiersEx.java is 7", 7.0, resultOfAnalysis);
    }

    /**
     * Tests that we get the correct result when analysing multiple classes
     */
    @Test
    public void testMultipleClasses(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/LengthOfIdentifiers", false);
        LengthOfIdentifiersAnalysis lengthOfIdentifiersAnalysis = new LengthOfIdentifiersAnalysis();
        lengthOfIdentifiersAnalysis.performAnalysis(launcher);
        double resultOfAnalysis = lengthOfIdentifiersAnalysis.getClassLengthOfIdentifiersScores().get("LengthOfIdentifiersEx");
        double resultOfAnalysis2 = lengthOfIdentifiersAnalysis.getClassLengthOfIdentifiersScores().get("LoIEx2");
        assertEquals("The average length of identifiers for LengthOfIdentifiersEx.java is 7", 7.0, resultOfAnalysis);
        assertEquals("The average length of identifiers for LoIEx2.java is 4", 4.0, resultOfAnalysis2);
    }
    @Test
    public void testNoteworthy(){
        //TODO: Complete this once you've finalised structure of noteworthy identifiers
    }
}
