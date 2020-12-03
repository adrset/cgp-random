package cgp.lib.node;

import cgp.lib.function.method.ArityFunction;
import cgp.lib.node.adapter.ConnectionAdapter;

import java.util.*;

public class NodeWithMemory<T> extends Node<T> {
    private LinkedList<T> memorisedValues;
    private int memoryLength;
    private List<Integer> memoryInputLocations;

    public NodeWithMemory(ArityFunction<T> fun, ConnectionAdapter<T> adapter, T defaultValue, int memoryLength) {
        super(fun, adapter, defaultValue);
        this.memorisedValues = new LinkedList<>();
        this.memoryLength = memoryLength;
        memoryInputLocations = new ArrayList<>();
    }

    @Override
    public void init(){
        super.init();
        // Populate memory register with default values
        for (int i = 0; i < memoryLength; i++) {
            remember(super.currentValue);
        }
    }


    /**
     * Overriding without using super
     * @return
     */
    @Override
    public T compute(){
        T value = super.compute();
        remember(value);
        return value;
    }

    private void remember(T val) {
        if (memorisedValues.size() >= memoryLength) {
            memorisedValues.removeFirst();
        }
        memorisedValues.add(val);
    }


    // p - połączenie z aktualną wartością czy z którąś z poprzednich (losowe połączenie - (nr node, nr komórki w rejestrze))




}
