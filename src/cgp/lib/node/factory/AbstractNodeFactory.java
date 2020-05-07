package cgp.lib.node.factory;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.simulation.mutator.IMutator;
import cgp.lib.node.Node;
import cgp.lib.input.InputParams;

public abstract class AbstractNodeFactory<T> {
    FunctionFactory factory;
    InputParams params;
    IMutator mutator;
    public AbstractNodeFactory(InputParams params, FunctionFactory factory, IMutator mutator){
        this.factory = factory;
        this.params = params;
        this.mutator = mutator;
    }
    public abstract Node<T> getNode();
    public abstract Node<T> getInputNode(T value);
    public abstract Node<T> getOutputNode();
    public abstract Node<T> getMutatedNode();
}
