package cgp.user.simulation.input;

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
