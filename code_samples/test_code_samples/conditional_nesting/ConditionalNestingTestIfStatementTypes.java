import ConditionalTest_Level1;

public class ConditionalTest {

    public static void main(String[] args) {
        System.out.println("STARTED");
        ConditionalTest_Level1 ct = new ConditionalTest_Level1();
    }

    public void test1() {
        if(true) {
            int i = a;
        }
    }
    public void test2() {
        if(true) {
            int i = 1;
        } else {
            int i = 1;
        }
    }
    public void test3() {
        if(true) {
            int i = 1;
        } else if(true) {
            int i = 1;
        } else {
            int i = 1;
        }
    }
    public void test4() {
        if(true) {
            int i = 1;
        } else if(true) {
                int i = 1;
        } else {
            if(true) {
                int i = 1;
            }
        }
    }
    public void test5() {
        if(true) {
            int i = 1;
        } else if(true) {
                int i = 1;
        } else {
            if(true) {
                int i = 1;
            }
        }
    }
    public void test6() {
        if(true) {
            int i = 1;
        } else if(true) {
            if(true) {
                if(true) {
                    int i = 1;
                }
            }
        } else {
            if(true) {
                int i = 1;
            }
        }
    }
    public void test7() {
        if(true) {
            int i = 1;
        } else if(true) {
            int i = 1;
        } else {
            if(true) {
                int i = 1;
            } else {
                if(true) {
                    int i = 1;
                }
            }
        }
    }
    public void test8() {
        if(true) {
            int i = 1;
        } else if(true) {
            int i = 1;
        } else {
            if(true) {
                int i = 1;
            } else {
                if(true) {
                    int i = 1;
                }
                else {
                    int i = 1;
                }
            }
        }
    }
    public void test9() {
        if(true) {
            int i = 1;
        } else if(true) {
            int i = 1;
        } else {
            if(true) {
                if(true) {
                    int i = 1;
                }
            }
            else {
                int i = 1;
            }
        }
    }
    public void test10() {
        if(true) {
            int i = 1;
        } else if(true) {
            int i = 1;
        } else {
            if(true) {
                if(true) {
                    int i = 1;
                } else {
                    if(true) {
                        int i = 1;
                    }
                }
            }
            else {
                int i = 1;
            }
        }
    }
}