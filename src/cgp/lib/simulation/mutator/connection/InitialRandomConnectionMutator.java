package cgp.lib.simulation.mutator.connection;

import cgp.user.simulation.input.InputParams;;
import cgp.lib.node.Node;
import cgp.lib.node.adapter.ConnectionAdapter;

import java.util.List;

public class InitialRandomConnectionMutator<T> extends RandomConnectionMutator<T> {
    public InitialRandomConnectionMutator(InputParams params) {
        super(params);
    }

    @Override
    public List<Node<T>> mutate(List<Node<T>> nodes) {


        for (int ii = params.getInputs(); ii < nodes.size(); ii++) {
            Node<T> node = nodes.get(ii);

            ConnectionAdapter<T> adapter = node.getAdapter();
            List<Node<T>> adapterNodes = adapter.getNodes();
            List<Node<T>> availableNodes = nodes.subList(0, ii - 1);
            for (int kk = 0; kk < adapterNodes.size(); kk++) {
                super.updateNode(kk, adapterNodes, availableNodes);

            }
        }

        return nodes;
    }
}
