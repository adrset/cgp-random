package cgp.node.factory;

import cgp.function.factory.RandomFunctionFactory;
import cgp.node.Node;
import cgp.node.adapter.ConnectionAdapter;
import cgp.simulation.input.InputParams;

public class NodeFactory extends AbstractNodeFactory {
    public NodeFactory(InputParams params){
        super(params, new RandomFunctionFactory());
    }

    public Node getNode(){

        return new Node(factory.getFunction(), new ConnectionAdapter(params.getMaxArity()));
    }

}
