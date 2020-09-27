package cgp.lib.simulation.evaluation;

import cgp.lib.individual.Individual;
import cgp.lib.individual.pojo.samples.Sample;

import java.util.Iterator;
import java.util.List;

public abstract class AbstractEvaluate<T> {
    public abstract double evaluate();

    protected List<Sample<T>> samples;

    public Individual<T> getFittest(List<Individual<T>> individuals) {
        Individual<T> fittest = null;
        Iterator<Individual<T>> iterator = individuals.iterator();
        while (iterator.hasNext()) {
            Individual<T> next = iterator.next();

            if (fittest == null) {
                fittest = next;
            } else {
                if (Double.isNaN(next.getFitness()) || Double.isInfinite(next.getFitness())) {
                    continue;
                }
                if (next.getFitness() < fittest.getFitness() ||
                        next.getFitness() == fittest.getFitness() && !next.isParent() && fittest.isParent()) {
                    fittest = next;
                }
            }

        }

        return fittest;
    }


    public AbstractEvaluate(List<Sample<T>> samples) {
        this.samples = samples;
    }

    public List<Sample<T>> getSamples() {
        return samples;
    }
}
