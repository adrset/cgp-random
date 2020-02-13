package cgp.simulation.mutator;

import cgp.node.Node;

public interface IMutator {
    Node[] mutateConnections (Node nodes[]);
    Node[] mutateFunctions (Node nodes[]);

}
