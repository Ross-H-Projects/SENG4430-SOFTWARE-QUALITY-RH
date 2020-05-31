package group3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        AppTest.class,
        DepthInheritanceTreeAnalysisTest.class,
        FanOutTest.class,
        CouplingTest.class,
        HalsteadComplexityTest.class,
        LengthOfIdentifiersTest.class,
        //ConditionalTest.class,
        //CommentTest.class,
        LackOfCohesionTest.class,
        FogIndexTest.class
})

public class TestSuite {
}