package cgp.lib.node;

import cgp.lib.function.method.ArityFunction;
import cgp.lib.node.adapter.ConnectionAdapter;

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
