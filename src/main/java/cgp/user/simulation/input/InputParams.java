package cgp.user.simulation.input;

public class InputParams {
    static InputParams singleton;

    private Config config;
    int memoryLength = 10;
    private double minError = 0.01;
    private int generationThreshold = 10000;

    int individuals = 5;

    public void setConfig(Config config) {
        this.config = config;
    }

    public int getIndividuals() {
        return individuals;
    }

    public int getMaxArity() {
        return config.getMaxArity();
    }


    public float getMutationProbability() {
        return mutationProbability;
    }
    public float getRecursiveConnectionProbability() {
        return recursiveConnectionProbability;
    }

    float mutationProbability = 0.1f;
    float recursiveConnectionProbability = 0.1f;


    InputParams() {
        config = new Config();
    }


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
        return config.getNodeAmount();
    }

    public int getInputs() {
        return config.getInputs();
    }

    public int getOutputs() {
        return config.getOutputs();
    }

}
