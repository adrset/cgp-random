package cgp.lib.simulation.evaluation;

import cgp.lib.individual.Individual;

public interface IEvaluate<T> {
    public void evaluate(Individual<T> individual);
}
