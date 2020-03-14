package group3;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
	    CtClass l = Launcher.parseClass("class A { void m() { System.out.println(\"yeah\");} }");
	    System.out.println(l);
    }
}
