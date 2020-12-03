package cgp.lib.node.factory;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.simulation.input.Config;
import cgp.lib.simulation.mutator.IMutator;
import cgp.lib.node.Node;

public abstract class AbstractNodeFactory<T> {
    FunctionFactory factory;
    Config config;
    public AbstractNodeFactory(Config config, FunctionFactory factory){
        this.factory = factory;
        this.config = config;
    }

    public FunctionFactory getFunctionFactory(){
        return factory;
    }
    public abstract Node<T> getNode();
    public abstract Node<T> getInputNode();
    public abstract Node<T> getOutputNode();
}
