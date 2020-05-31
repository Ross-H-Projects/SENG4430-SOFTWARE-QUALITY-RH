 public class FanOut1 {
    public void fanOut1_1() {
        FanOut2 f = new FanOut2();
        f.fanOut2_1();
        f.fanOut2_2();
    }
}

public class FanOut2 {
    public void fanOut2_1() {
        FanOut1 f = new FanOut1();
        f.fanOut1_1();
    }

    public void fanOut2_2() {
        fanOut2_1();
    }
}

public class FanOut3 {
    public FanOut3() {
        f1 = new FanOut1();
        f3 = new FanOut1();
        f2 = new FanOut2();
    }
}

public class FanOut4 {
    public void fanOut4_1() {
        FanOut2 f = new FanOut2();
        f.fanOut2_1();
        f.fanOut2_1();
        f.fanOut2_2();
    }
}