package cgp.lib.simulation.evaluation;

import cgp.lib.individual.Individual;
import cgp.lib.individual.samples.Sample;

import java.util.List;

public interface IEvaluate<T> {
    public double evaluate(Individual<T> individual);

    List<Sample<T>> getSamples();
}
