package cgp.simulation.node;

import cgp.function.method.ArityFunction;
import cgp.simulation.node.adapter.ConnectionAdapter;

public interface INode {
    void setStrategy(ArityFunction f);
    ArityFunction getStrategy();
    ConnectionAdapter getAdapter();
    double evaluate();
}
