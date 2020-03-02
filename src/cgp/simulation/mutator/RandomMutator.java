package cgp.simulation.mutator;

import cgp.function.factory.FunctionFactory;
import cgp.function.method.ArityFunction;
import cgp.individual.Individual;
import cgp.node.Node;
import cgp.node.adapter.ConnectionAdapter;
import cgp.input.InputParams;

import java.util.List;
import java.util.Random;

public class RandomMutator<T> implements IMutator<T> {
    Random generator;
    InputParams params;
    FunctionFactory factory;
    public RandomMutator(InputParams params, FunctionFactory factory){
        generator = new Random();
        this.params = params;
        this.factory = factory;
    }

    @Override
    public Individual<T> mutate(Individual<T> ind) {
        Node<T>[] nodes = ind.getNodes();
        for (int ii=0; ii< nodes.length; ii++) {
            Node<T> node = nodes[ii];
            ConnectionAdapter adapter = node.getAdapter();
            List<Node> adapterNodes = adapter.getNodes();
            for (int kk = 0; kk < adapterNodes.size(); kk++) {
                double randomDouble = generator.nextDouble();
                if (randomDouble > 1.0 - params.getMutationProbability()) {
                    adapterNodes.set(kk, getRandomNode(nodes));
                }
            }
        }

        ind.setNodes(nodes);
        return mutateFunctions(ind);
    }

    public Individual<T> mutateFunctions(Individual<T> ind) {
        Node<T>[] nodes = ind.getNodes();
        for (int ii=0; ii< nodes.length; ii++) {

            Node<T> node = nodes[ii];
            double randomDouble = generator.nextDouble();
            if (randomDouble > 1.0 - params.getMutationProbability()) {
                try {
                    ArityFunction f = factory.getFunction();
                    node.setStrategy(f);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        ind.setNodes(nodes);
        return ind;
    }

    Node<T> getRandomNode(Node<T>[] nodes){
        int randomIndex1 = generator.nextInt(nodes.length);
        return nodes[randomIndex1];
    }
}
