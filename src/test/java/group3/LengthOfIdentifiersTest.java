package group3;

import group3.metric_analysis.length_of_identifiers.LengthOfIdentifiersAnalysis;
import spoon.Launcher;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Unit tests for Length of identifiers metric
 * @author DanielSands
 */
public class LengthOfIdentifiersTest {
    //TODO: Use tracker class instead of analysis class, make sure flags/options work
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

    /**
     * Tests that noteworthy identifiers are all correct
     */
    @Test
    public void testNoteworthy(){
        Launcher launcher = Utilities.importCodeSample("code_samples/test_code_samples/LengthOfIdentifiers", false);
        LengthOfIdentifiersAnalysis lengthOfIdentifiersAnalysis = new LengthOfIdentifiersAnalysis();
        lengthOfIdentifiersAnalysis.performAnalysis(launcher);
        HashMap<String, List<String>> noteworthyLengthOfIdentifierScores = lengthOfIdentifiersAnalysis.getNoteworthyLengthOfIdentifierScores();
        ArrayList<String> noteworthyEx = (ArrayList<String>) noteworthyLengthOfIdentifierScores.get("LengthOfIdentifiersEx");
        assertEquals("LengthOfIdentifiersEx contains the following noteworthy: calc, ab and acdc", true, (noteworthyEx.contains("calc") &&
                noteworthyEx.contains("ab") && noteworthyEx.contains("acdc") && noteworthyEx.size() == 3)) ;
    }
}
