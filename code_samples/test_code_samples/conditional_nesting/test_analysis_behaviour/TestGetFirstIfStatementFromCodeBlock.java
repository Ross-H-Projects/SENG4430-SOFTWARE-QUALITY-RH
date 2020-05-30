public class TestGetFirstIfStatementFromCodeBlock {
    public static void testGetFirstIfStatementFromCodeBlock1() {
        if (true) {
            int i = 1;
        }
    }
    public static void testGetFirstIfStatementFromCodeBlock2() {
    }
    public static void testGetFirstIfStatementFromCodeBlock3() {
        if (true) {
            int i = 1;
        } else if(true) {}
    }
    public static void testGetFirstIfStatementFromCodeBlock4() {
        if (true) {
            int i = 1;
        }
        if (true) {
            int i = 2;
        }
    }
}