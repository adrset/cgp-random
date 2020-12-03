package cgp.lib.node;

import cgp.lib.function.method.ArityFunction;
import cgp.lib.node.adapter.ConnectionAdapter;
import cgp.lib.node.adapter.MemoryConnectionAdapter;

import java.util.*;

public class NodeWithMemory<T> extends Node<T> {
    private LinkedList<T> memorisedValues;
    private int memoryLength;

    public NodeWithMemory(ArityFunction<T> fun, MemoryConnectionAdapter<T> adapter, T defaultValue, int memoryLength) {
        super(fun, adapter, defaultValue);
        this.memorisedValues = new LinkedList<>();
        this.memoryLength = memoryLength;
    }

    public NodeWithMemory() {
        this.memorisedValues = new LinkedList<>();
    }

    @Override
    public void init() {
        super.init();
        // Populate memory register with default values
        for (int i = 0; i < memoryLength; i++) {
            remember(super.currentValue);
        }
    }


    /**
     * Overriding without using super
     *
     * @return
     */
    @Override
    public T compute() {
        T value = super.compute();
        remember(value);
        return value;
    }

    protected void remember(T val) {
        if (memorisedValues.size() >= memoryLength) {
            memorisedValues.removeFirst();
        }
        memorisedValues.add(val);
    }


    // p - połączenie z aktualną wartością czy z którąś z poprzednich (losowe połączenie - (nr node, nr komórki w rejestrze))

    @Override
    public Node<T> clone() {
        NodeWithMemory<T> clone = new NodeWithMemory<>();
        if (this.strategy != null) {
            clone.setStrategy((ArityFunction<T>) this.strategy.clone());
        }
        clone.setUID(this.UID);
        clone.currentValue = this.currentValue;
        clone.memoryLength = this.memoryLength;
        for (int i = 0; i < memoryLength; i++) {
            clone.remember(this.memorisedValues.get(i));
        }
        return clone;
    }


}
