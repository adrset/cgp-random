package cgp.lib.individual.pojo.samples;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Sample<T> {
    @JsonProperty("input")
    List<T> input;
    @JsonProperty("output")
    List<T> output;

    List<T> computedOutput;

    public List<T> getComputedOutput() {
        return computedOutput;
    }

    public void setComputedOutput(List<T> computedOutput) {
        this.computedOutput = computedOutput;
    }

    public Sample() {

    }
    @JsonProperty("input")
    public List<T> getInput() {
        return input;
    }

    @JsonProperty("input")
    public void setInput(List<T> input) {
        this.input = input;
    }

    @JsonProperty("output")
    public void setOutput(List<T> output) {
        this.output = output;
    }

    @JsonProperty("output")
    public List<T> getOutput() {
        return output;
    }

}
