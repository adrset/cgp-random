package cgp.node;

import cgp.function.method.ArityFunction;
import cgp.node.adapter.ConnectionAdapter;

public class OutputNode<T> extends Node<T> {
    public OutputNode(ArityFunction fun, ConnectionAdapter adapter, T defaultValue) {
       super(fun, adapter, defaultValue);
    }
}
