package cgp.lib.node;

import cgp.lib.node.adapter.ConnectionAdapter;

public class InputNode<T> extends Node<T> {
    public InputNode(T defaultValue) {
       super(null, new ConnectionAdapter<>(0), defaultValue);
    }

    @Override
    public Node<T> clone() {
        Node<T> clone = new InputNode<>(super.currentValue);
        clone.setStrategy(null);
        clone.setUID(this.UID);
        clone.setAdapter(this.adapter.clone());
        return clone;
    }
}
