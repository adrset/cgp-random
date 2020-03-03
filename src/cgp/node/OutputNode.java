package cgp.node;

import java.util.List;

public class OutputNode<T> extends Node<T> {
    T value;

    public OutputNode(T value) {
        super();
        this.value = value;
    }

    public OutputNode() {
        this(null);
    }


    void setValue(T t) {
        this.value = t;
    }

    public T evaluate() {
        // call adapter
        // adapter;
        List<Node> in = adapter.getNodes();

        return value;
    }


}
