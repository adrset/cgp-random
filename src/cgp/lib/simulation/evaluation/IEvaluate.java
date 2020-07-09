package cgp.lib.simulation.evaluation;

import cgp.lib.individual.Individual;

public interface IEvaluate<T> {
    public double evaluate(Individual<T> individual);
}
