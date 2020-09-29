package cgp.lib.simulation.mutator.connection.resursive;

import cgp.lib.node.Node;
import cgp.lib.node.adapter.ConnectionAdapter;
import cgp.lib.simulation.input.Config;
import cgp.lib.simulation.mutator.connection.RandomConnectionMutator;

import java.util.List;

public class RecursiveRandomConnectionMutator<T> extends RandomConnectionMutator<T> {
    public RecursiveRandomConnectionMutator(Config config) {
        super(config);
    }

    @Override
    public List<Node<T>> mutate(List<Node<T>> nodes) {

        // Only mutating
        for (int ii = config.getInputs(); ii < nodes.size() - config.getOutputs(); ii++) {
            Node<T> node = nodes.get(ii);
            ConnectionAdapter<T> adapter = node.getAdapter();
            List<Node<T>> adapterNodes = adapter.getNodes();

            for (int kk = 0; kk < adapterNodes.size(); kk++) {
                double randomDouble = generator.nextDouble();
                if (randomDouble > 1.0 - config.getMutationProbability()) {
                    double recursiveRandom = generator.nextDouble();
                    if (recursiveRandom > 1.0 - config.getRecursiveConnectionProbability()) {
                        adapterNodes.set(kk, getRandomNode(nodes.subList(ii, nodes.size() - config.getOutputs())));
                    } else {
                        adapterNodes.set(kk, getRandomNode(nodes.subList(0, ii)));
                    }
                }
            }
        }

        return nodes;
    }

}
