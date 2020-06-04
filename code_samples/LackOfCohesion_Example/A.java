public class A {
    int a;
    B[] b;

    A () {
        a = 1;
        b = new B[4];
    }

    public void arb1 () {
        a = 1;
    }

    public int arb2() {
        return a;
    }

    public void arb3() {
        String j = "abc 123";
    }
}