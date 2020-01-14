package cgp.simulation.node.factory;

import cgp.function.factory.IFunctionFactory;
import cgp.function.factory.RandomFunctionFactory;
import cgp.simulation.mutator.IMutator;
import cgp.simulation.mutator.RandomMutator;
import cgp.simulation.node.Node;
import cgp.simulation.node.adapter.ConnectionAdapter;
import cgp.simulation.input.InputParams;

public class NodeFactory extends AbstractNodeFactory {
    public NodeFactory(InputParams params, IFunctionFactory factory, IMutator mutator){

        super(params, factory, mutator);
    }

    public Node getNode(){

        return new Node(factory.getFunction(), new ConnectionAdapter(params.getMaxArity()));
    }

    @Override
    public Node getMutatedNode() {
        return null;
    }

}
