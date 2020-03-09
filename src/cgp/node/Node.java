package cgp.node;

import cgp.function.method.ArityFunction;
import cgp.node.adapter.ConnectionAdapter;

import java.util.ArrayList;
import java.util.List;

public class Node <T>{
    ArityFunction<T> strategy;
    ConnectionAdapter<T> adapter;
    private static int counter = 0;
    protected int UID;

    // Default value required by recurrent CGP
    // Could be initial value for input nodes
    T defaultValue;
    public Node(ArityFunction fun, ConnectionAdapter adapter, T defaultValue) {
        this.strategy = fun;
        this.adapter = adapter;
        this.defaultValue = defaultValue;
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
        return UID;
    }

    public void setUID(int uid) {
        this.UID = uid;
    }

    public T evaluate() {
        List<Node> inputs = adapter.getNodes();
        List<T> inputValues = new ArrayList<>();
        for(Node inputNode: inputs){
            inputValues.add((T) inputNode.evaluate());
        }

        return strategy.calculate(inputValues);
    }

    public void printAdapter(){

        for (Node<T> n : adapter.getNodes()) {
            //n.printAdapter();
            //System.out.println(n.getUID());
        }
    }


    @Override
    public Node<T> clone() {
        Node<T> clone = new Node<>();
        if (this.strategy != null){
            clone.setStrategy((ArityFunction<T>) this.strategy.clone());

        }
        clone.setUID(this.UID);
        return clone;
    }
}
