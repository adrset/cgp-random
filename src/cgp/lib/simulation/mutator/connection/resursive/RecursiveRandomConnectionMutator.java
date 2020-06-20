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

        for (int ii = params.getInputs(); ii < nodes.size(); ii++) {
            Node<T> node = nodes.get(ii);
            ConnectionAdapter adapter = node.getAdapter();
            List<Node<T>> adapterNodes = adapter.getNodes();

            for (int kk = 0; kk < adapterNodes.size(); kk++) {
                double randomDouble = generator.nextDouble();
                if (randomDouble > 1.0 - params.getMutationProbability()) {
                    adapterNodes.set(kk, getRandomNode(nodes));
                }
            }
        }

        return nodes;
    }

}
