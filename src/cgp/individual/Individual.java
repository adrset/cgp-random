package cgp.individual;

import cgp.simulation.mutator.IMutator;
import cgp.node.Node;
import cgp.node.adapter.ConnectionAdapter;
import cgp.node.factory.AbstractNodeFactory;
import cgp.input.InputParams;

import java.util.ArrayList;
import java.util.List;

public class Individual<T> implements IIndividual<T> {
    private int nodeNo;
    private Node<T> cartesian[];
    private InputParams params;
    AbstractNodeFactory<T> factory;

    public Individual(int nodeNo, InputParams params, AbstractNodeFactory<T> factory) {
        this.nodeNo = nodeNo;
        this.inputs = new Node[params.getInputs()];
        this.outputs = new Node[params.getOutputs()];
        this.factory = factory;
        this.params = params;
    }

    private Node<T> inputs[];
    private Node<T> outputs[];

    @Override
    public void init(IMutator mutator) {
        this.cartesian = new Node[nodeNo];

        for (int i = 0; i < this.cartesian.length; i++) {
            this.cartesian[i] = factory.getNode();
        }

        cartesian = mutator.mutateConnections(this.cartesian);

    }

    public void setNodes(Node<T>[] nodes) {
        this.cartesian = nodes;
    }

    @Override
    public double evaluate() {

        for (int i = 0; i < this.cartesian.length; i++) {
            // Recurrent evaluation here
            System.out.println(cartesian[i].getStrategy().getClass());

        }
        return 0;
    }

    @Override
    public void setInputs(Node<T>[] in) {
        this.inputs = in;
    }

    @Override
    public void setOutputs(Node<T>[] out) {
        this.outputs = out;
    }

    @Override
    public void mutate(IMutator mutator) {
        cartesian = mutator.mutateFunctions(cartesian);
        cartesian = mutator.mutateConnections(cartesian);
    }

    @Override
    public IIndividual clone() {
        Individual ind = new Individual(this.nodeNo, this.params, this.factory);
        Node cartesianCopy[] = new Node[this.nodeNo];
        for (int i = 0; i < this.cartesian.length; i++) {
            cartesianCopy[i] = (Node) this.cartesian[i].clone();

        }
        // Now let's create new adapters and recreate them basing on original ones
        for (int i = 0; i < this.cartesian.length; i++) {

            List<Node> inputs = this.cartesian[i].getAdapter().getNodes();
            ConnectionAdapter adapter = new ConnectionAdapter(this.cartesian[i].getAdapter().getMaxArity());
            List<Node> newInputs = new ArrayList<>();

            for (Node<T> id : inputs) {
                if (id == null) {
                    //Basically means that no node is connected there
                    continue;
                }
                Node<T> foundNode = getNodeWithUID(this.cartesian, id.getUID());
                if (foundNode == null) {
                    System.err.println("Severe error!!");
                    continue;
                }
                newInputs.add(foundNode);
            }
            adapter.setInputs(newInputs);
            this.cartesian[i].setAdapter(adapter);

        }
        ind.setNodes(cartesianCopy);

        return ind;
    }

    private Node<T> getNodeWithUID(Node<T>[] nodes, int uid) {
        for (Node<T> node : nodes) {

            int aUID = node.getUID();
            if (aUID == uid) {
                return node;
            }

        }

        return null;
    }

}
