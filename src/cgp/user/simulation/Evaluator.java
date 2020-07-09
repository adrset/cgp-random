package cgp.user.simulation;

import cgp.lib.individual.Individual;
import cgp.lib.individual.samples.Sample;
import cgp.lib.simulation.evaluation.IEvaluate;

import java.util.List;

public class Evaluator<Double> implements IEvaluate<Double> {

    List<Sample<Double>> samples;

    public Evaluator(List<Sample<Double>> samples) {
        this.samples = samples;
    }


    @Override
    public double evaluate(Individual<Double> individual) {
        List<Double> outputs = individual.getOutput();
        for (Double o : outputs) {

        }

        return 0.;
    }
}
