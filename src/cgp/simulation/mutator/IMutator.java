package cgp.simulation.mutator;

import cgp.individual.Individual;

public interface IMutator <T> {
    Individual<T> mutate(Individual<T> ind);

}
