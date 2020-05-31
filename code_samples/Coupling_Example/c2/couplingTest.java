import java.util.*;
public class couplingTest{
	public D d;


	public static void main(String[] args) {
		A a = new A();
		B b = new B();
		C c = new C();
		d = new D();
		C c2;
		c2 = new C();

		B.goomba();
		theMethod(a, d);
		theOtherMethod();
	}

	public void theMethod(A a){
		a.aMethod();
		if(d.dopeStuff() == true){

		}
	}

	public static void theOtherMethod(){
		int yeah = c.cMethod();
		c2.yeahright();
	}

}