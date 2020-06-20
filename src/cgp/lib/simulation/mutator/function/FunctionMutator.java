package cgp.lib.simulation.mutator.function;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.function.method.ArityFunction;
import cgp.lib.node.Node;
import cgp.lib.simulation.mutator.IMutator;
import cgp.user.simulation.input.InputParams;

import java.util.List;
import java.util.Random;

public class FunctionMutator<T> implements IMutator<T> {
    Random generator;
    InputParams params;
    FunctionFactory factory;

    public FunctionMutator(InputParams params, FunctionFactory factory) {
        generator = new Random();
        this.params = params;
        this.factory = factory;
    }

    @Override
    public List<Node<T>> mutate(List<Node<T>> nodes) {
        for (int ii = 0; ii < nodes.size(); ii++) {

            Node<T> node = nodes.get(ii);
            double randomDouble = generator.nextDouble();
            if (randomDouble > 1.0 - params.getMutationProbability()) {
                try {
                    ArityFunction f = factory.getFunction();
                    node.setStrategy(f);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return nodes;
    }
}
