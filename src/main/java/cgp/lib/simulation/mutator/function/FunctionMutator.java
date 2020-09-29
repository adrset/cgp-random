package cgp.lib.simulation.mutator.function;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.function.method.ArityFunction;
import cgp.lib.node.Node;
import cgp.lib.simulation.input.Config;
import cgp.lib.simulation.mutator.IMutator;

import java.util.List;
import java.util.Random;

public class FunctionMutator<T> implements IMutator<T> {
    Random generator;
    Config config;
    FunctionFactory<T> factory;

    public FunctionMutator(Config config, FunctionFactory<T> factory) {
        generator = new Random();
        this.config = config;
        this.factory = factory;
    }

    @Override
    public List<Node<T>> mutate(List<Node<T>> nodes) {
        for (Node<T> node : nodes) {

            double randomDouble = generator.nextDouble();
            if (randomDouble > 1.0 - config.getMutationProbability()) {
                try {
                    ArityFunction<T> f = factory.getFunction();
                    node.setStrategy(f);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }
}
