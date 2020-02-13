package cgp.simulation.mutator;

import cgp.function.factory.FunctionFactory;
import cgp.simulation.input.InputParams;;
import cgp.simulation.node.Node;
import cgp.simulation.node.adapter.ConnectionAdapter;

import java.util.List;

public class InitialRandomMutator extends RandomMutator {
    public InitialRandomMutator(InputParams params, FunctionFactory factory){
        super(params, factory);
    }

    @Override
    public Node[] mutateConnections(Node[] nodes) {

        for (int ii=0; ii< nodes.length; ii++) {
            Node node = nodes[ii];
            ConnectionAdapter adapter = node.getAdapter();
            List<Node> adapterNodes = adapter.getNodes();
            for (int kk = 0; kk < adapterNodes.size(); kk++) {

                adapterNodes.set(kk, getRandomNode(nodes));

            }
        }

        return nodes;
    }
}
