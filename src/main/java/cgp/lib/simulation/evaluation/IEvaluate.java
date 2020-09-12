package cgp.lib.simulation.evaluation;

import cgp.lib.individual.Individual;
import cgp.lib.individual.pojo.samples.Sample;

import java.util.List;

public interface IEvaluate<T> {
    double evaluate();

    List<Sample<T>> getSamples();
    Individual<T> getFittest(List<Individual<T>> individuals);
}
