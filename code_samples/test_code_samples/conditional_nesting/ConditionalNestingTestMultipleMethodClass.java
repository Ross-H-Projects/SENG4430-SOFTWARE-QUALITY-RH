import ConditionalTest_Level1;

public class ConditionalTest {

    public static void main(String[] args) {
        System.out.println("STARTED");
        ConditionalTest_Level1 ct = new ConditionalTest_Level1();
    }

    public void test1() {
        if(true) {
            int i = 4;
        } else {
            int i = 1;
            if(true) {
                int i = 5;
                if(true) {
                    int i = 6;
                }
            }
        }
    }
    public void test2() {
        if(10==20) {
            int i = 10;
        } else if(30==40) {
            int i = 342;
        } else {
            int i = 1;
            if(true) {
                if(true) {
                    int i = 2;
                } else {
                    int i = 2;
                    if(true) {
                        int i = 3;
                    } else {
                        int u = 3;
                        if (true) {
                            int a = 4;
                        }
                    }
                }
            }
        }
    }
}