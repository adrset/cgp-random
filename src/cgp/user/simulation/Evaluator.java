package cgp.user.simulation;

import cgp.lib.individual.Individual;
import cgp.lib.node.OutputNode;
import cgp.lib.simulation.evaluation.IEvaluate;

import java.util.List;

public class Evaluator<Double> implements IEvaluate<Double> {

    @Override
    public void evaluate(Individual<Double> individual) {
        //List<OutputNode<Double>> outputs = individual.getOutputNodes();
        // Compare outputs with user values
    }
}
