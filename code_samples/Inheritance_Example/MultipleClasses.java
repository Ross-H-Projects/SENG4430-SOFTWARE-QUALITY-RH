
public class A {
    protected int aMember;

    A() {
        System.out.println("A constructor");
    }

}


public class B extends A {
    protected int bMemmber;

    B () {
        super();
        System.out.println("B constructor");
    }
}


public class C extends B {
    protected int cMember;

    C() {
        super();
        System.out.println("C constructor");
    }


}