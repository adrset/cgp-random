package cgp.individual;

import cgp.node.OutputNode;
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
    List<Node<T>> allNodes;

    private Node<T> inputs[];
    private Node<T> outputs[];

    public Individual(int nodeNo, InputParams params, AbstractNodeFactory<T> factory) {
        this.nodeNo = nodeNo;
        this.inputs = new Node[params.getInputs()];
        this.outputs = new Node[params.getOutputs()];
        this.factory = factory;
        this.params = params;
        allNodes = new ArrayList<>();
        for (int i=0; i< params.getOutputs(); i++) {
            outputs[i] = new OutputNode<>();
        }

    }


    @Override
    public void init(IMutator mutator) {
        this.cartesian = new Node[nodeNo];

        for (int i = 0; i < this.cartesian.length; i++) {
            this.cartesian[i] = factory.getNode();
        }
        for (Node i:inputs) {
            allNodes.add(i);
        }
        for (Node c:cartesian) {
            allNodes.add(c);
        }
        for (Node o:outputs) {
            allNodes.add(o);
        }
        allNodes = mutator.mutateConnections(allNodes);

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

    public void mutate(IMutator mutator) {
        allNodes = mutator.mutateFunctions(allNodes);
        allNodes = mutator.mutateConnections(allNodes);
    }

    public List<Node<T>> getAllNodes(){
        return allNodes;
    }

    @Override
    public void describe() {
        for (Node n: cartesian){
            ConnectionAdapter<T> a = n.getAdapter();
            StringBuilder stringBuilder = new StringBuilder();
            List<Node> nodes = a.getNodes();
            for (int i = 0; i < nodes.size(); i++) {

                stringBuilder.append(nodes.get(i).getUID() + ", ");

            }
            System.out.println(n.getUID() + " -> [" + stringBuilder.toString() + "]");
        }
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
