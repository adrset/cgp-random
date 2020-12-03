package cgp.lib.node.factory;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.node.InputNode;
import cgp.lib.node.Node;
import cgp.lib.node.NodeWithMemory;
import cgp.lib.node.OutputNode;
import cgp.lib.node.adapter.ConnectionAdapter;
import cgp.lib.node.adapter.MemoryConnectionAdapter;
import cgp.lib.simulation.input.Config;

public class MemoryNodeFactory<T> extends NodeFactory<T> {
    private int memoryLength;
    public MemoryNodeFactory(Config config, FunctionFactory<T> factory, T defaultValue){

        super(config, factory, defaultValue);
        this.memoryLength = config.getMemoryLength();
    }

    @Override
    public Node<T> getNode(){
        Node<T> n = null;
        try {
            n = new NodeWithMemory<>(factory.getFunction(), new MemoryConnectionAdapter<>(config.getMaxArity()), defaultValue, memoryLength);
            n.init();
            return n;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
