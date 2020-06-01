public class FanIn1 {
    public void FanIn1_1() {
        FanIn2 f = new FanIn2();
        f.FanIn2_1();
        f.FanIn2_2();
    }
}

public class FanIn2 {
    public void FanIn2_1() {
        FanIn1 f = new FanIn1();
        f.FanIn1_1();
    }

    public void FanIn2_2() {
        FanIn2_1();
    }
}

public class FanIn3 {
    public FanIn3() {
        f1 = new FanIn1();
        f3 = new FanIn1();
        f2 = new FanIn2();
    }
}