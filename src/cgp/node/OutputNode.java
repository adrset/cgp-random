package cgp.node;

import cgp.function.method.ArityFunction;
import cgp.node.adapter.ConnectionAdapter;

public class OutputNode<T> extends Node<T> {
    public OutputNode(ArityFunction fun, ConnectionAdapter adapter, T defaultValue) {
       super(fun, adapter, defaultValue);
    }

    public OutputNode() {
        super();
    }

    @Override
    public Node<T> clone() {
        Node<T> clone = new OutputNode<>();
        if (this.strategy != null){
            clone.setStrategy((ArityFunction<T>) this.strategy.clone());

        }
        clone.setUID(super.UID);
        return clone;
    }
}
