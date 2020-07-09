package cgp.lib.individual.samples;

import java.util.List;

public class Sample<T> {
    List<T> inputs;
    List<T> outputs;

    public Sample(List<T> inputs, List<T> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }


}
