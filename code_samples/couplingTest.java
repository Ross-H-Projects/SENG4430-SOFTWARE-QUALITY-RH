public class A{
	
	A(){
		B b = new B();
	}
}

public class B{
	B(){

	}
}

public class C{
	C(){

	}
}

public class D{
	D(){

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

	}

	public void sex(){
		
	}

}