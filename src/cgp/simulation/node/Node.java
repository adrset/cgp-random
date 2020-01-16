package cgp.simulation.node;

import cgp.function.method.ArityFunction;
import cgp.simulation.node.adapter.ConnectionAdapter;

public class Node implements INode{
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
    @Override
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

    @Override
    public ArityFunction getStrategy() {
        return strategy;
    }

    @Override
    public ConnectionAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void setAdapter(ConnectionAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getUID() {
        return counter;
    }

    public void setUID(int uid) {
        this.UID = uid;
    }

    @Override
    public double evaluate() {
        return 0;
    }

    @Override
    public INode clone() {
        Node clone = new Node();
        clone.setStrategy((ArityFunction) this.strategy.clone());
        clone.setUID(this.UID);
        return clone;
    }
}
