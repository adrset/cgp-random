package cgp.user.simulation;

import cgp.lib.individual.Individual;
import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.simulation.evaluation.IEvaluate;

import java.util.Iterator;
import java.util.List;

public class Evaluator implements IEvaluate<Double> {

    List<Sample<Double>> samples;

    public Evaluator(List<Sample<Double>> samples) {
        this.samples = samples;
    }


    @Override
    public double evaluate() {
        double sum = 0;
        for (Sample<Double> sample : samples) {

            for (int i = 0; i < sample.getOutput().size(); i++){

                sum += Math.abs(sample.getOutput().get(i) - sample.getComputedOutput().get(i));

            }
        }
        //sum = sum / samples.size();

        return Math.sqrt(sum);
    }

    @Override
    public List<Sample<Double>> getSamples() {
        return samples;
    }

    @Override

    public Individual<Double> getFittest(List<Individual<Double>> individuals) {
        Individual<Double> fittest = null;
        Iterator<Individual<Double>> iterator = individuals.iterator();
        while (iterator.hasNext()) {
            Individual<Double> next =  iterator.next();
            if (fittest == null) {
                fittest = next;
            } else {
                if (next.getFitness() < fittest.getFitness()){
                    fittest = next;
                }
            }

        }

        return fittest;
    }
}
