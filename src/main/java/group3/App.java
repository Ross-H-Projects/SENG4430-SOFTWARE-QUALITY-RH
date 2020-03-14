package group3;

import spoon.Launcher;
import spoon.reflect.CtModel;
import spoon.reflect.declaration.CtClass;

public class App 
{
    public static void main( String[] args )
    {
        ConfigLoader cfg;
        String codeSampleFileName;
        String codeSampleFilePath;

        cfg = new ConfigLoader();
        //codeSampleFileName = cfg.getProperty("codeSampleFileName");
        //codeSampleFilePath = String.format("resources/code_samples/%s.java");

        //Launcher launcher = new Launcher();
        //launcher.addInputResource(codeSampleFilePath);
        //launcher.buildModel();
        //CtModel model = launcher.getModel();
        //CtClass l = Launcher.parseClass("class A { void m() { System.out.println(\"yeah\");} }");
        //System.out.println(l);
    }
}