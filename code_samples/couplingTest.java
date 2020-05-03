public class A{
	
	A(){
		B b = new B();
	}

	public void dumb(){
		C bruh = new C();
	}

}

public class B{
	B(){

	}
}

public class C{
	public static int yum;

	C(){

	}

	public int cum(){
		return 5;
	}
}

public class D{
	D(){
		B suck = new B();

	}
}

public class couplingTest{
	public static void main(String[] args ){
		A a = new A();
		B b = new B();
		C c = new C();
		D d = new D();	
	}

	public void gay(){
		a.dumb();
	}

	public void sex(){
		int yeah = c.yum;
	}

}