package cgp.simulation.node.factory;

import cgp.function.factory.IFunctionFactory;
import cgp.simulation.mutator.IMutator;
import cgp.simulation.node.Node;
import cgp.simulation.input.InputParams;

public abstract class AbstractNodeFactory {
    IFunctionFactory factory;
    InputParams params;
    IMutator mutator;
    public AbstractNodeFactory(InputParams params, IFunctionFactory factory, IMutator mutator){
        this.factory = factory;
        this.params = params;
        this.mutator = mutator;
    }
    public abstract Node getNode();
    public abstract Node getMutatedNode();
}
