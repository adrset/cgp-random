package cgp.user.simulation;

import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.simulation.evaluation.AbstractEvaluate;

import java.util.List;

public class FibEvaluator extends AbstractEvaluate<Double> {

    private static final Integer BIG_PRIZE = 0;
    private static final Integer SMALL_PRIZE = 10;
    private static final Integer BIG_PENALTY = 1000;
    private static final Integer SMALL_PENALTY = 100;
    private static final Double MIN_ERROR = 0.000001d;

    public double evaluate() {
        double sum = 0;
        boolean firstNonMatching = false;
        Integer tmp;

        for (Sample<Double> sample : samples) {

            for (int i = 0; i < sample.getOutput().size(); i++) {
                tmp = BIG_PENALTY;
                boolean matching = Math.abs(sample.getComputedOutput().get(i) - sample.getOutput().get(i)) < MIN_ERROR;
                if (!matching && !firstNonMatching) {
                    firstNonMatching = true;
                }

                if (matching) {
                    tmp = firstNonMatching ? SMALL_PRIZE : BIG_PRIZE;
                }

                sum += tmp;

            }
        }
        //sum = sum / samples.size();

        return sum;
    }

    public FibEvaluator(List<Sample<Double>> samples) {
        super(samples);
    }


}
