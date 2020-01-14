package cgp.simulation.node;

import cgp.function.method.ArityFunction;
import cgp.simulation.node.adapter.ConnectionAdapter;

public class Node implements INode{
    ArityFunction strategy;
    ConnectionAdapter adapter;
    public Node(ArityFunction fun, ConnectionAdapter adapter) {
        this.strategy = fun;
        this.adapter = adapter;
    }

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
    public double evaluate() {
        return 0;
    }

}
