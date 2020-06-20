package cgp.lib.simulation.mutator.connection;

import cgp.lib.simulation.mutator.IMutator;
import cgp.user.simulation.input.InputParams;
import cgp.lib.node.Node;
import cgp.lib.node.adapter.ConnectionAdapter;

import java.util.List;
import java.util.Random;

public class RandomConnectionMutator<T> implements IMutator<T> {
    protected Random generator;
    protected InputParams params;

    public RandomConnectionMutator(InputParams params) {
        generator = new Random();
        this.params = params;
    }

    @Override
    public List<Node<T>> mutate(List<Node<T>> nodes) {

        for (int ii = params.getInputs(); ii < nodes.size(); ii++) {
            Node<T> node = nodes.get(ii);
            ConnectionAdapter adapter = node.getAdapter();
            List<Node<T>> adapterNodes = adapter.getNodes();
            List<Node<T>> availableNodes = nodes.subList(0, ii - 1);
            for (int kk = 0; kk < adapterNodes.size(); kk++) {
                double randomDouble = generator.nextDouble();
                if (randomDouble > 1.0 - params.getMutationProbability()) {
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
