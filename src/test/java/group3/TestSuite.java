package group3;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        AppTest.class,
        DepthInheritanceTreeAnalysisTest.class,
        FanOutTest.class,
        CouplingTest.class,
        LengthOfIdentifiersTest.class,
        LackOfCohesionTest.class,
        FogIndexTest.class
})

public class TestSuite {
}