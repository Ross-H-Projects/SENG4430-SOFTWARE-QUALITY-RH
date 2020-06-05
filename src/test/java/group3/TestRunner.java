package group3;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Class<?> testToRun;
        if (args.length == 0) {
            testToRun = TestSuite.class;
        } else {
            testToRun = getTestClass(args[0]);
        }
        Result result = JUnitCore.runClasses(testToRun);
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

    private static Class<?> getTestClass(String metric) {
        Class<?> testClass;
        switch (metric) {
            case "app_test":
                testClass = AppTest.class;
                break;
            case "depth_inheritance":
                testClass = DepthInheritanceTreeAnalysisTest.class;
                break;
            case "fan_in":
                testClass = FanInTest.class;
                break;
            case "fan_out":
                testClass = FanOutTest.class;
                break;
            case "coupling":
                testClass = CouplingTest.class;
                break;
            case "halstead":
                testClass = HalsteadComplexityTest.class;
                break;
            case "length_of_identifiers":
                testClass = LengthOfIdentifiersTest.class;
                break;
            case "conditional":
                testClass = ConditionalTest.class;
                break;
            case "comments":
                testClass = CommentTest.class;
                break;
            case "cohesion":
                testClass = LackOfCohesionTest.class;
                break;
            case "fog_index":
                testClass = FogIndexTest.class;
                break;
            case "cyclomatic_complexity":
                testClass = CyclomaticComplexityTest.class;
                break;
            case "method_count":
                testClass = MethodCountTest.class;
                break;
            default:
                throw new IllegalArgumentException("Test " + metric + " is invalid");
        }
        return testClass;
    }
}