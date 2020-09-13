package cgp.lib.simulation.mutator.connection.resursive;

import cgp.lib.node.Node;
import cgp.lib.node.adapter.ConnectionAdapter;
import cgp.user.simulation.input.InputParams;

import java.util.List;

public class InitialRecursiveRandomConnectionMutator<T> extends RecursiveRandomConnectionMutator<T> {
    public InitialRecursiveRandomConnectionMutator(InputParams params) {
        super(params);
    }

    @Override
    public List<Node<T>> mutate(List<Node<T>> nodes) {

        for (int ii = super.params.getInputs(); ii < nodes.size(); ii++) {
            Node<T> node = nodes.get(ii);

            ConnectionAdapter<T> adapter = node.getAdapter();
            List<Node<T>> adapterNodes = adapter.getNodes();
            for (int kk = 0; kk < adapterNodes.size(); kk++) {

                double recursiveRandom = generator.nextDouble();
                if (recursiveRandom > 1.0 - params.getRecursiveConnectionProbability()) {
                    adapterNodes.set(kk, getRandomNode(nodes.subList(0, nodes.size() - params.getOutputs())));
                } else {
                    adapterNodes.set(kk, getRandomNode(nodes.subList(0, ii)));
                }

            }
        }

        return nodes;
    }
}