package cgp.lib.node.factory;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.simulation.mutator.IMutator;
import cgp.lib.node.Node;
import cgp.user.simulation.input.InputParams;

public abstract class AbstractNodeFactory<T> {
    FunctionFactory factory;
    InputParams params;
    IMutator mutator;
    public AbstractNodeFactory(InputParams params, FunctionFactory factory){
        this.factory = factory;
        this.params = params;
    }
    public abstract Node<T> getNode();
    public abstract Node<T> getInputNode(T value);
    public abstract Node<T> getOutputNode();
}
