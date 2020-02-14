package cgp.simulation.mutator;

import cgp.node.Node;

public interface IMutator <T> {
    Node<T>[] mutateConnections (Node<T> nodes[]);
    Node<T>[] mutateFunctions (Node<T> nodes[]);

}
