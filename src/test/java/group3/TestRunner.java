package group3;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestSuite.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }



        String testResultMessage;
        if (result.wasSuccessful()) {
            testResultMessage = "\nAll " + result.getRunCount() + " test(s) ran with no errors.";
        } else {
            testResultMessage = "\nFailure, " + result.getFailureCount() + " of " + result.getRunCount() + " test(s) failed.";
        }

        System.out.println(testResultMessage);
    }
}