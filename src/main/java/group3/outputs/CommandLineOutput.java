package group3.outputs;

import group3.Output;

import java.util.ArrayList;

// Basic output class
public class CommandLineOutput extends Output {

    public CommandLineOutput(String[] args){}

    // Just output string to the command line
    @Override
    public void create(ArrayList<String> results) {
        for (String s : results) {
            System.out.println(s);
        }
    }
}
