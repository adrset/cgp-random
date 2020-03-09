package cgp.simulation.mutator;

import cgp.function.factory.FunctionFactory;
import cgp.function.method.ArityFunction;
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
    public List<Node<T>> mutateConnections(List<Node<T>> nodes) {

        for (int ii=0; ii< nodes.size(); ii++) {
            Node<T> node = nodes.get(ii);
            ConnectionAdapter adapter = node.getAdapter();
            List<Node> adapterNodes = adapter.getNodes();
            for (int kk = 0; kk < adapterNodes.size(); kk++) {
                double randomDouble = generator.nextDouble();
                if (randomDouble > 1.0 - params.getMutationProbability()) {
                    adapterNodes.set(kk, getRandomNode(nodes, nodes.indexOf(node)));
                }
            }
        }

        return nodes;
    }

    @Override
    public List<Node<T>> mutateFunctions(List<Node<T>> nodes) {
        for (int ii=0; ii< nodes.size(); ii++) {

            Node<T> node = nodes.get(ii);
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
        return nodes;
    }

    Node<T> getRandomNode(List<Node<T>> nodes, int index){

        int randomIndex1;

        do {
            randomIndex1 = generator.nextInt(nodes.size() - params.getOutputs());
        } while (randomIndex1 == index);

        return nodes.get(randomIndex1);
    }
}
