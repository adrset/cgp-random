package cgp.node;

import cgp.function.method.ArityFunction;
import cgp.node.adapter.ConnectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class InputNode<T> extends Node<T> {
    T value;

    public InputNode(ConnectionAdapter adapter, T defaultValue) {
        super(null, adapter, defaultValue);
    }

    void setValue(T t) {
        this.value = t;
    }

    public T evaluate() {
        // call adapter
        // adapter;
        List<Node> out = adapter.getNodes();

        return value;
    }


}
