package group3;

import group3.outputs.CommandLineOutput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Outputs {
    private ArrayList<Output> outputs;

    public Outputs (String[] outputDefinitions) {
        outputs = new ArrayList<>();
        for (String def : outputDefinitions) {

            String arr[] = def.split(" ");
            Output output;
            switch (arr[0]) {
                case "cmd":
                    output = new CommandLineOutput(Arrays.copyOfRange(arr, 1, arr.length));
                    break;
                default:
                    throw new IllegalArgumentException("Output " + arr[0] + " is invalid");

            }
            outputs.add(output);
        }
    }

    public void create(ArrayList<String> results) {
        for (Output output : outputs) {
            output.create(results);
        }
    }

    public List<Output> getOutputs() {
        return outputs;
    }
}
