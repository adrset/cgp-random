package cgp.simulation.mutator;

import cgp.function.factory.IFunctionFactory;
import cgp.node.INode;
import cgp.node.adapter.ConnectionAdapter;
import cgp.simulation.input.InputParams;

import java.util.List;
import java.util.Random;

public class RandomMutator implements IMutator {
    Random generator;
    InputParams params;
    IFunctionFactory factory;
    public RandomMutator(InputParams params, IFunctionFactory factory){
        generator = new Random();
        this.params = params;
        this.factory = factory;
    }

    @Override
    public void mutateConnections(INode[][] nodes) {

        for (int ii=0; ii< nodes.length; ii++)
            for (int jj = 0; jj < nodes[ii].length; jj++) {
                INode node = nodes[ii][jj];
                ConnectionAdapter adapter = node.getAdapter();
                List<INode> adapterNodes = adapter.getNodes();
                for (int kk=0;kk<adapterNodes.size();kk++){
                    double randomDouble = generator.nextDouble();
                    if (randomDouble > 1.0 - params.getMutationProbability()) {
                        adapterNodes.set(kk, getRandomNode(nodes));
                    }
                }
            }

    }

    @Override
    public void mutateFunctions(INode[][] nodes) {
        for (int ii=0; ii< nodes.length; ii++)
            for (int jj = 0; jj < nodes[ii].length; jj++) {
                INode node = nodes[ii][jj];
                double randomDouble = generator.nextDouble();
                if (randomDouble > 1.0 - params.getMutationProbability()) {
                    node.setStrategy(factory.getFunction());
                }
            }
    }

    INode getRandomNode(INode[][] nodes){
        int randomIndex1 = generator.nextInt(nodes.length);
        int randomIndex2 = generator.nextInt(nodes.length);
        return nodes[randomIndex1][randomIndex2];
    }
}
