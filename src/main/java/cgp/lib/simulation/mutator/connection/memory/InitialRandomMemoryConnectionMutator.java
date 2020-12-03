package cgp.lib.simulation.mutator.connection.memory;

import cgp.lib.node.Node;
import cgp.lib.node.adapter.ConnectionAdapter;
import cgp.lib.simulation.input.Config;

import java.util.List;

public class InitialRandomMemoryConnectionMutator<T> extends RandomMemoryConnectionMutator<T> {
    public InitialRandomMemoryConnectionMutator(Config params) {
        super(params);
    }

    @Override
    public List<Node<T>> mutate(List<Node<T>> nodes) {

        for (int ii = config.getInputs(); ii < nodes.size(); ii++) {
            Node<T> node = nodes.get(ii);

            ConnectionAdapter<T> adapter = node.getAdapter();
            List<Node<T>> adapterNodes = adapter.getNodes();
            List<Node<T>> availableNodes = nodes.subList(0, ii);
            for (int kk = 0; kk < adapterNodes.size(); kk++) {
                super.updateNode(kk, adapterNodes, availableNodes);
            }
        }

        return nodes;
    }
}
