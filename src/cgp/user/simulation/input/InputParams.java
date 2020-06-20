package cgp.user.simulation.input;

public class InputParams {
    static InputParams singleton;
    int nodeAmount = 10;
    int inputs = 2;
    int outputs = 1;
    int maxArity = 2;
    int memoryLength = 10;
    double minError = 0.01;
    int generationThreshold = 200;

    int individuals = 5;


    public int getIndividuals() {
        return individuals;
    }

    public int getMaxArity() {
        return maxArity;
    }

    public float getMutationProbability() {
        return mutationProbability;
    }
    public float getRecursiveConnectionProbability() {
        return recursiveConnectionProbability;
    }

    float mutationProbability = 0.1f;
    float recursiveConnectionProbability = 0.1f;


    InputParams() {    }


    public double getMinError() {
        return minError;
    }

    public int getGenerationThreshold() {
        return generationThreshold;
    }

    public static InputParams getSingleton() {
        return singleton;
    }

    public static synchronized InputParams getInstance() {
        if (singleton == null) {
            singleton = new InputParams();
        }
        return singleton;
    }

    public int getNodeAmount() {
        return nodeAmount;
    }

    public int getInputs() {
        return inputs;
    }

    public int getOutputs() {
        return outputs;
    }

}
