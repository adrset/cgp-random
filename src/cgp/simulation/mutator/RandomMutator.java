package cgp.simulation.mutator;

import cgp.function.factory.FunctionFactory;
import cgp.function.method.ArityFunction;
import cgp.simulation.node.Node;
import cgp.simulation.node.adapter.ConnectionAdapter;
import cgp.simulation.input.InputParams;

import java.util.List;
import java.util.Random;

public class RandomMutator implements IMutator {
    Random generator;
    InputParams params;
    FunctionFactory factory;
    public RandomMutator(InputParams params, FunctionFactory factory){
        generator = new Random();
        this.params = params;
        this.factory = factory;
    }

    @Override
    public Node[] mutateConnections(Node[] nodes) {

        for (int ii=0; ii< nodes.length; ii++) {
            Node node = nodes[ii];
            ConnectionAdapter adapter = node.getAdapter();
            List<Node> adapterNodes = adapter.getNodes();
            for (int kk = 0; kk < adapterNodes.size(); kk++) {
                double randomDouble = generator.nextDouble();
                if (randomDouble > 1.0 - params.getMutationProbability()) {
                    adapterNodes.set(kk, getRandomNode(nodes));
                }
            }
        }

        return nodes;
    }

    @Override
    public Node[] mutateFunctions(Node[] nodes) {
        for (int ii=0; ii< nodes.length; ii++) {

            Node node = nodes[ii];
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

    Node getRandomNode(Node[] nodes){
        int randomIndex1 = generator.nextInt(nodes.length);
        return nodes[randomIndex1];
    }
}
