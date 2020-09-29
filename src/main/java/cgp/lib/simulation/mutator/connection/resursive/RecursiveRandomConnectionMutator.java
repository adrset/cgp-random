package cgp.lib.simulation.mutator.connection.resursive;

import cgp.lib.node.Node;
import cgp.lib.node.adapter.ConnectionAdapter;
import cgp.lib.simulation.mutator.connection.RandomConnectionMutator;
import cgp.user.simulation.input.InputParams;

import java.util.List;

public class RecursiveRandomConnectionMutator<T> extends RandomConnectionMutator<T> {
    public RecursiveRandomConnectionMutator(InputParams params) {
        super(params);
    }

    @Override
    public List<Node<T>> mutate(List<Node<T>> nodes) {

        // Only mutating
        for (int ii = params.getInputs(); ii < nodes.size() - params.getOutputs(); ii++) {
            Node<T> node = nodes.get(ii);
            ConnectionAdapter<T> adapter = node.getAdapter();
            List<Node<T>> adapterNodes = adapter.getNodes();

            for (int kk = 0; kk < adapterNodes.size(); kk++) {
                double randomDouble = generator.nextDouble();
                if (randomDouble > 1.0 - params.getMutationProbability()) {
                    double recursiveRandom = generator.nextDouble();
                    if (recursiveRandom > 1.0 - params.getRecursiveConnectionProbability()) {
                        adapterNodes.set(kk, getRandomNode(nodes.subList(ii, nodes.size() - params.getOutputs())));
                    } else {
                        adapterNodes.set(kk, getRandomNode(nodes.subList(0, ii)));
                    }
                }
            }
        }

        return nodes;
    }

}
