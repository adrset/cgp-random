package cgp.lib.node;

import cgp.lib.node.adapter.ConnectionAdapter;

public class InputNode<T> extends Node<T> {
    public InputNode(T defaultValue) {
       super(null, new ConnectionAdapter(0), defaultValue);
    }
}
