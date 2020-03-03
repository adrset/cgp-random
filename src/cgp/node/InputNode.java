package cgp.node;

import java.util.ArrayList;
import java.util.List;

public class InputNode<T> extends Node<T> {
    T value;

    public InputNode(T value) {
        super();
        this.value = value;
    }

    public InputNode() {
        this(null);
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
