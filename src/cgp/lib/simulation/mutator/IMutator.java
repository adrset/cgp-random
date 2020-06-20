package cgp.lib.simulation.mutator;

import cgp.lib.node.Node;

import java.util.List;

public interface IMutator <T> {
    List<Node<T>> mutate (List<Node<T>> nodes);
}
