package cgp.simulation.node.factory;

import cgp.function.factory.FunctionFactory;
import cgp.simulation.mutator.IMutator;
import cgp.simulation.node.Node;
import cgp.simulation.input.InputParams;

public abstract class AbstractNodeFactory {
    FunctionFactory factory;
    InputParams params;
    IMutator mutator;
    public AbstractNodeFactory(InputParams params, FunctionFactory factory, IMutator mutator){
        this.factory = factory;
        this.params = params;
        this.mutator = mutator;
    }
    public abstract Node getNode();
    public abstract Node getMutatedNode();
}
