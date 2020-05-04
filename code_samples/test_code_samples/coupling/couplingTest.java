public class A{
	
	A(){
		B b = new B();
	}

	public void aMethod(){
		C cObject = new C();
	}

}

public class B{
	B(){

	}
}

public class C{
	public static int cInteger;

	C(){

	}

	public int cMethod(){
		return 5;
	}
}

public class D{
	D(){
		B bObject = new B();

	}

	public boolean dopeStuff(){
		boolean alright = true;
		return alright;
	}
}

public class couplingTest{
	public static void main(String[] args ){
		A a = new A();
		B b = new B();
		C c = new C();
		D d = new D();	
	}

	public void theMethod(){
		a.aMethod();
		if(d.dopeStuff() == true){

		}
	}

	public void theOtherMethod(){
		int yeah = c.cMethod;
	}

}