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

public class C extends A{
	public static int cInteger;

	C(){
		System.out.println("I'm gay");
	}

	public void yeahright(){
		System.out.println("I'm gay");
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
/*
public interface E{
	public void yeahright();
}
*/
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