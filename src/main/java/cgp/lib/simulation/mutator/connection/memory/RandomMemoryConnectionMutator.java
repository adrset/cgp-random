package cgp.lib.simulation.mutator.connection.memory;

import cgp.lib.node.Node;
import cgp.lib.node.NodeWithMemory;
import cgp.lib.node.adapter.ConnectionAdapter;
import cgp.lib.node.adapter.MemoryConnectionAdapter;
import cgp.lib.simulation.input.Config;
import cgp.lib.simulation.mutator.IMutator;

import java.util.List;
import java.util.Random;

public class RandomMemoryConnectionMutator<T> implements IMutator<T> {
    protected Random generator;
    protected Config config;

    public RandomMemoryConnectionMutator(Config config) {
        generator = new Random();
        this.config = config;
    }

    @Override
    public List<Node<T>> mutate(List<Node<T>> nodes) {

        for (int ii = config.getInputs(); ii < nodes.size(); ii++) {
            Node<T> node = nodes.get(ii);
            ConnectionAdapter<T> adapter = node.getAdapter();

            List<Node<T>> adapterNodes = adapter.getNodes();
            List<Node<T>> availableNodes = nodes.subList(0, ii);
            for (int kk = 0; kk < adapterNodes.size(); kk++) {
                double randomDouble = generator.nextDouble();
                if (randomDouble > 1.0 - config.getMutationProbability()) {
                    double memoryMutation = generator.nextDouble();
                    if (memoryMutation > 1.0 - config.getMemoryConnectionProbability() && adapter instanceof MemoryConnectionAdapter) {
                        memoryMutation(kk, adapter, adapterNodes, availableNodes);
                    } else {
                        updateNode(kk, adapterNodes, availableNodes);
                    }
                }
            }

        }

        return nodes;
    }

    private void memoryMutation(int kk, ConnectionAdapter<T> adapter, List<Node<T>> adapterNodes, List<Node<T>> availableNodes) {
        MemoryConnectionAdapter<T> memoryConnectionAdapter = (MemoryConnectionAdapter) adapter;
        updateNode(kk, adapterNodes, availableNodes);
        Node<T> theNode = adapterNodes.get(kk);
        List<Integer> inputLocations = memoryConnectionAdapter.getInputLocations();
        int memorySize = inputLocations.size();
        if (theNode instanceof NodeWithMemory) {
            int rand = generator.nextInt(memorySize);
            inputLocations.set(kk, rand);
        } else {
            inputLocations.set(kk, 0);
        }
    }


    protected void updateNode(int index, List<Node<T>> adapterNodes, List<Node<T>> availableNodes) {
        adapterNodes.set(index, getRandomNode(availableNodes));
    }

    protected Node<T> getRandomNode(List<Node<T>> nodes) {

        int randomIndex1 = generator.nextInt(nodes.size());

        return nodes.get(randomIndex1);
    }
}
