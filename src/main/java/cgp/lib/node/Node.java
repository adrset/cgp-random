package cgp.lib.node;

import cgp.lib.function.method.ArityFunction;
import cgp.lib.node.adapter.ConnectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    ArityFunction<T> strategy;
    ConnectionAdapter<T> adapter;
    private static int counter = 0;

    private boolean active = false;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public T getCurrentValue() {
        return currentValue;
    }


    public void setCurrentValue(T currentValue) {
        this.currentValue = currentValue;
    }

    protected int UID;

    // Default value required by recurrent CGP
    // Could be initial value for input nodes
    T currentValue;

    public Node(ArityFunction<T> fun, ConnectionAdapter<T> adapter, T defaultValue) {
        this.strategy = fun;
        this.adapter = adapter;
        this.currentValue = defaultValue;
    }


    /**
     * Use only for completely new nodes!!! Not cloned ones!!!
     */
    public void init() {
        this.UID = counter++;
    }

    /**
     * Only for copy!!!
     */
    public Node() {
    }

    public void setStrategy(ArityFunction<T> f) {
        this.strategy = f;
    }

    public ArityFunction<T> getStrategy() {
        return strategy;
    }

    public ConnectionAdapter<T> getAdapter() {
        return adapter;
    }

    public void setAdapter(ConnectionAdapter<T> adapter) {
        this.adapter = adapter;
    }

    public int getUID() {
        return UID;
    }

    public void setUID(int uid) {
        this.UID = uid;
    }

    public T compute() {
        List<Node<T>> inputs = adapter.getNodes();
        List<T> inputValues = new ArrayList<>();
        for (Node<T> inputNode : inputs) {
            inputValues.add(inputNode.getCurrentValue());
        }

        currentValue = strategy.calculate(inputValues);
        return currentValue;
    }

    public void printAdapter() {

        for (Node<T> n : adapter.getNodes()) {
            n.printAdapter();
            //System.out.println(n.getUID());
        }
    }


    @Override
    public Node<T> clone() {
        Node<T> clone = new Node<>();
        if (this.strategy != null) {
            clone.setStrategy((ArityFunction<T>) this.strategy.clone());
        }
        clone.setUID(this.UID);
        clone.currentValue = this.currentValue;

        return clone;
    }
}
