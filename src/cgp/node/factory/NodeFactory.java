package cgp.node.factory;

import cgp.function.factory.IFunctionFactory;
import cgp.function.factory.RandomFunctionFactory;
import cgp.node.Node;
import cgp.simulation.input.InputParams;

public abstract class NodeFactory {
    IFunctionFactory factory;
    InputParams params;
    public NodeFactory(InputParams params, IFunctionFactory factory){
        this.factory = factory;
        this.params = params;
    }
    public abstract Node getNode();
}
