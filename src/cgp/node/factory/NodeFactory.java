package cgp.node.factory;

import cgp.function.factory.FunctionFactory;
import cgp.simulation.mutator.IMutator;
import cgp.node.Node;
import cgp.node.adapter.ConnectionAdapter;
import cgp.input.InputParams;

public class NodeFactory<T> extends AbstractNodeFactory {
    public NodeFactory(InputParams params, FunctionFactory factory, IMutator mutator){

        super(params, factory, mutator);
    }

    public Node<T> getNode(){
        Node<T> n = null;
        try {
            n = new Node<>(factory.getFunction(), new ConnectionAdapter(params.getMaxArity()));
            n.init();
            return n;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Node<T> getMutatedNode() {
        return null;
    }

}
