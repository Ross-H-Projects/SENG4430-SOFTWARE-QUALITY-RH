

public class Example {
    public int a;
    public int b;
    public List<Integer>  u;


    Example() {
        u = new List<Integer>();
    }

    public void arb1 () {
        //a = 1;
        //u = new List<Integer>();
    }

    public void arb2 () {
       //a = 2;
    }
}

public class OtherExample {
    public int a;
    public int b;
    public Example ex = new Example();
    public LinkedList<Integer> list = new LinkedList<Integer>();

    OtherExample() {
    }

    public void arb1 () {
        ex.a = 1;
        ex.a = 2;
    }

    public void arb2 () {
        a = 2;
        ex.a = 3;
        ex.a = 4;
    }


}