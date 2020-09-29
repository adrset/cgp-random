package cgp.lib.simulation.input;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Config{
    @JsonProperty("nodes")
    private int nodeAmount = 10;
    @JsonProperty("inputs")
    private int inputs = 1;
    @JsonProperty("outputs")
    private int outputs = 1;
    @JsonProperty("maxArity")
    private int maxArity = 2;
    @JsonProperty("mutationProbability")
    float mutationProbability = 0.1f;

    @JsonProperty("recursiveConnectionProbability")
    float recursiveConnectionProbability = 0.1f;

    @JsonProperty("memoryLength")
    int memoryLength = 10;
    @JsonProperty("minError")
    private double minError = 0.01;
    @JsonProperty("generationThreshold")
    private int generationThreshold = 100000;

    int individuals = 5;


    public double getMinError() {
        return minError;
    }

    public float getMutationProbability() {
        return mutationProbability;
    }

    public float getRecursiveConnectionProbability() {
        return recursiveConnectionProbability;
    }

    public int getMemoryLength() {
        return memoryLength;
    }

    public int getGenerationThreshold() {
        return generationThreshold;
    }

    public int getIndividuals() {
        return individuals;
    }

    public int getInputs() {
        return inputs;
    }

    public int getMaxArity() {
        return maxArity;
    }

    public int getNodeAmount() {
        return nodeAmount;
    }

    public int getOutputs() {
        return outputs;
    }
};
