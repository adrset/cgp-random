package cgp.user.simulation;

import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.simulation.evaluation.AbstractEvaluate;

import java.util.List;

public class AdderEvaluator extends AbstractEvaluate<Boolean> {


    public double evaluate() {
        double sum = 0;
        for (Sample<Boolean> sample : samples) {

            for (int i = 0; i < sample.getOutput().size(); i++) {

                sum += (sample.getOutput().get(i) == sample.getComputedOutput().get(i)) ? 0 : 10;

            }
        }
        //sum = sum / samples.size();

        return sum;
    }

    public AdderEvaluator(List<Sample<Boolean>> samples) {
        super(samples);
    }


}
