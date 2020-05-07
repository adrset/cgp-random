package cgp.lib.simulation.mutator;

import cgp.lib.node.Node;

import java.util.List;

public interface IMutator <T> {
    List<Node<T>> mutateConnections (List<Node<T>> nodes);
    List<Node<T>> mutateFunctions (List<Node<T>> nodes);
}
