package cgp.lib.node;

import cgp.lib.function.method.ArityFunction;
import cgp.lib.node.adapter.ConnectionAdapter;

import java.util.*;

public class NodeWithMemory<T> extends Node<T> {
    private LinkedList<T> memorisedValues;
    private int memoryLength;

    public NodeWithMemory(ArityFunction<T> fun, ConnectionAdapter<T> adapter, T defaultValue, int memoryLength) {
        super(fun, adapter, defaultValue);
        this.memorisedValues = new LinkedList<>();
        this.memoryLength = memoryLength;
    }

    @Override
    public T compute(){
        T val = super.compute();
        remember(val);
        return val;
    }

    private void remember(T val) {
        if (memorisedValues.size() >= memoryLength) {
            memorisedValues.removeFirst();
        }
        memorisedValues.add(val);
    }

    // p - połączenie z aktualną wartością czy z którąś z poprzednich (losowe połączenie - (nr node, nr komórki w rejestrze))




}
