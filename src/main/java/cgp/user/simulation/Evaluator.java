package cgp.user.simulation;

import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.simulation.evaluation.AbstractEvaluate;

import java.util.List;

public class Evaluator extends AbstractEvaluate<Double> {


    public double evaluate() {
        double sum = 0;
        for (Sample<Double> sample : samples) {

            for (int i = 0; i < sample.getOutput().size(); i++) {

                sum += Math.abs(sample.getOutput().get(i) - sample.getComputedOutput().get(i));

            }
        }
        //sum = sum / samples.size();

        return Math.sqrt(sum);
    }

    public Evaluator(List<Sample<Double>> samples) {
        super(samples);
    }


}
