package cgp.node;

import cgp.function.method.ArityFunction;
import cgp.node.adapter.ConnectionAdapter;

public interface INode {
    void setStrategy(ArityFunction f);
    ArityFunction getStrategy();
    ConnectionAdapter getAdapter();
    double evaluate();
}
