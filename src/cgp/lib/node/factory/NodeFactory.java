package cgp.lib.node.factory;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.node.OutputNode;
import cgp.lib.simulation.mutator.IMutator;
import cgp.lib.node.Node;
import cgp.lib.node.adapter.ConnectionAdapter;
import cgp.user.simulation.input.InputParams;

public class NodeFactory<T> extends AbstractNodeFactory<T> {
    T defaultValue;
    public NodeFactory(InputParams params, FunctionFactory factory, T defaultValue){

        super(params, factory);
        this.defaultValue = defaultValue;
    }

    public Node<T> getNode(){
        Node<T> n = null;
        try {
            n = new Node<>(factory.getFunction(), new ConnectionAdapter(params.getMaxArity()), defaultValue);
            n.init();
            return n;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Node<T> getInputNode(T value) {
        Node<T> n = null;
        try {
            n = new Node<>(null, new ConnectionAdapter(0), defaultValue);
            n.init();
            return n;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public Node<T> getOutputNode(){
        Node<T> n = null;
        try {
            n = new OutputNode<>(null, new ConnectionAdapter(params.getMaxArity()), defaultValue);
            n.init();
            return n;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
