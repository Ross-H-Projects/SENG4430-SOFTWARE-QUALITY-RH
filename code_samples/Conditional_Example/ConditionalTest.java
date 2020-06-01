import ConditionalTest_Level1;

public class ConditionalTest {
    public void test3() {
        if(true) {
            int i = 1;
        }
        if(true) {
            int i = 1;
            if(true) {
                int i=2;
            }
            else if(true) {
                int i=3;
            } else {
                int i = 4;
            }
        }
    }
}