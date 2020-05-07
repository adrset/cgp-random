package cgp.node;

import cgp.function.method.ArityFunction;
import cgp.node.adapter.ConnectionAdapter;

import java.util.*;

public class NodeWithMemory<T> extends Node<T> {
    private LinkedList<T> memorisedValues;
    private int memoryLength;

    public NodeWithMemory(ArityFunction fun, ConnectionAdapter adapter, T defaultValue, int memoryLength) {
        super(fun, adapter, defaultValue);
        this.memorisedValues = new LinkedList<>();
        this.memoryLength = memoryLength;
    }

    @Override
    public T evaluate(){
        T val = super.evaluate();
        remember(val);
        return val;
    }

    private void remember(T val) {
        if (memorisedValues.size() >= memoryLength) {
            memorisedValues.removeFirst();
        }
        memorisedValues.add(val);
    }




}
