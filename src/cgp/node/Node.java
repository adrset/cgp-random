package cgp.node;

import cgp.function.method.Function;
import cgp.node.adapter.ConnectionAdapter;

public class Node implements INode{
    Function strategy;
    ConnectionAdapter adapter;
    public Node(Function fun, ConnectionAdapter adapter) {
        this.strategy = fun;
        this.adapter = adapter;
    }

    public Node() {}

    public void setStrategy(Function f){
        this.strategy = f;
    }

    @Override
    public Function getStrategy() {
        return strategy;
    }

    @Override
    public ConnectionAdapter getAdapter() {
        return adapter;
    }

}
