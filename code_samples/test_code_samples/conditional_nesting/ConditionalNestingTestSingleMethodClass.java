import ConditionalTest_Level1;

public class ConditionalTest {

    public static void main(String[] args) {
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
}