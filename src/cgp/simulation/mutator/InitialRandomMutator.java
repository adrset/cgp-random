package cgp.simulation.mutator;

import cgp.function.factory.FunctionFactory;
import cgp.input.InputParams;;
import cgp.node.Node;
import cgp.node.adapter.ConnectionAdapter;

import java.util.List;

public class InitialRandomMutator<T> extends RandomMutator<T> {
    public InitialRandomMutator(InputParams params, FunctionFactory factory){
        super(params, factory);
    }

    @Override
    public List<Node<T>> mutateConnections(List<Node<T>> nodes) {

        for (int ii=0; ii< nodes.size(); ii++) {
            Node<T> node = nodes.get(ii);

            ConnectionAdapter<T> adapter = node.getAdapter();
            List<Node> adapterNodes = adapter.getNodes();
            for (int kk = 0; kk < adapterNodes.size(); kk++) {

                adapterNodes.set(kk, getRandomNode(nodes));

            }
        }

        return nodes;
    }
}
