
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


public class C extends A {
    protected int cMember;

    C() {
        super();
        System.out.println("C constructor");
    }
}

public class R extends B {
    protected int cMember;

    R() {
        super();
        System.out.println("R constructor");
    }
}

public class T extends B {
    protected int cMember;

    T() {
        super();
        System.out.println("T constructor");
    }
}

public class Y extends B {
    protected int cMember;

    Y() {
        super();
        System.out.println("Y constructor");
    }
}

public class P extends R {
    protected int cMember;

    P() {
        super();
        System.out.println("P constructor");
    }
}