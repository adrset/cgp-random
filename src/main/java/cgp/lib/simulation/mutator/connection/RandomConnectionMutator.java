package cgp.lib.simulation.mutator.connection;

import cgp.lib.simulation.input.Config;
import cgp.lib.simulation.mutator.IMutator;
import cgp.lib.node.Node;
import cgp.lib.node.adapter.ConnectionAdapter;

import java.util.List;
import java.util.Random;

public class RandomConnectionMutator<T> implements IMutator<T> {
    protected Random generator;
    protected Config config;

    public RandomConnectionMutator(Config config) {
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
                    updateNode(kk, adapterNodes, availableNodes);
                }
            }

        }

        return nodes;
    }


    protected void updateNode(int index, List<Node<T>> adapterNodes, List<Node<T>> availableNodes){
        adapterNodes.set(index, getRandomNode(availableNodes));
    }

    protected Node<T> getRandomNode(List<Node<T>> nodes) {

        int randomIndex1 = generator.nextInt(nodes.size());

        return nodes.get(randomIndex1);
    }
}
