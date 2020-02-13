package cgp.simulation.node;

import cgp.function.method.ArityFunction;
import cgp.simulation.node.adapter.ConnectionAdapter;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Node{
    ArityFunction strategy;
    ConnectionAdapter adapter;
    private static int counter = 0;
    private int UID;
    public Node(ArityFunction fun, ConnectionAdapter adapter) {
        this.strategy = fun;
        this.adapter = adapter;
    }

    /**
     * Use only for completely new nodes!!! Not cloned ones!!!
     */
    public void init(){
        this.UID = counter++;
    }

    /**
     * Only for copy!!!
     */
    public Node() {}

    public void setStrategy(ArityFunction f){
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
        return counter;
    }

    public void setUID(int uid) {
        this.UID = uid;
    }

    public double evaluate() {
        List<Node> inputs = adapter.getNodes();
        Double[] inputValues = new Double[inputs.size()];
        int i=0;
        for(Node inputNode: inputs){
            inputValues[i++] = inputNode.evaluate();
        }

        return strategy.calculate(inputValues);
    }

    @Override
    public Node clone() {
        Node clone = new Node();
        clone.setStrategy((ArityFunction) this.strategy.clone());
        clone.setUID(this.UID);
        return clone;
    }
}
