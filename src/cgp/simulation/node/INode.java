package cgp.simulation.node;

import cgp.function.method.ArityFunction;
import cgp.simulation.ICloneable;
import cgp.simulation.node.adapter.ConnectionAdapter;

public interface INode extends ICloneable {
    void setStrategy(ArityFunction f);
    ArityFunction getStrategy();
    void init();
    ConnectionAdapter getAdapter();
    void setAdapter(ConnectionAdapter adapter);
    int getUID();

    double evaluate();
}
