package group3.outputs;

import group3.Output;

import java.util.ArrayList;

public class CommandLineOutput extends Output {

    public CommandLineOutput(String[] args){}

    @Override
    public void create(ArrayList<String> results) {
        for (String s : results) {
            System.out.println(s);
        }
    }
}
