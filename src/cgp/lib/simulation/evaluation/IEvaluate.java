package cgp.lib.simulation.evaluation;

import cgp.lib.individual.IIndividual;

public interface IEvaluate<T> {
    public void evaluate(IIndividual<T> individual);
}
