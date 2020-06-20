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


    protected int UID;

    // Default value required by recurrent CGP
    // Could be initial value for input nodes
    T currentValue;

    public Node(ArityFunction fun, ConnectionAdapter adapter, T defaultValue) {
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

    public void setStrategy(ArityFunction f) {
        this.strategy = f;
    }

    public ArityFunction getStrategy() {
        return strategy;
    }

    public ConnectionAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(ConnectionAdapter adapter) {
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
        for (Node inputNode : inputs) {
            inputValues.add((T) inputNode.getCurrentValue());
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
        return clone;
    }
}
