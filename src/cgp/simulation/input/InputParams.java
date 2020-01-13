package cgp.simulation.input;

public class InputParams {
    static InputParams singleton;
    int columns = 20;
    int rows = 20;
    int inputs = 2;
    int outputs = 2;
    int maxArity = 2;

    public int getMaxArity() {
        return maxArity;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    double mutationProbability = 0.01f;


    InputParams() {
    }

    ;

    public static InputParams getSingleton() {
        return singleton;
    }

    public static synchronized InputParams getInstance() {
        if (singleton == null) {
            singleton = new InputParams();
        }
        return singleton;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getInputs() {
        return inputs;
    }

    public int getOutputs() {
        return outputs;
    }

}
