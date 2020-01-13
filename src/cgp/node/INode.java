package cgp.node;

import cgp.function.method.Function;
import cgp.node.adapter.ConnectionAdapter;

public interface INode {
    void setStrategy(Function f);
    Function getStrategy();
    ConnectionAdapter getAdapter();
}
