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
    private InputParams params;
    private AbstractNodeFactory<T> factory;
    private List<Node<T>> allNodes;
    private T[] inputValues;


    public Individual(int nodeNo, InputParams params, AbstractNodeFactory<T> factory, T[] inputValues) {
        this.inputValues = inputValues;
        this.nodeNo = nodeNo;
        this.factory = factory;
        this.params = params;
        allNodes = new ArrayList<>();


    }

    @Override
    public void init(IMutator mutator) {
        int basicNodesNo = params.getColumns() * params.getRows();
        int inputNodesNo = params.getInputs();
        int outputNodesNo = params.getOutputs();
        for (int i = 0; i < inputNodesNo; i++) {
            allNodes.add(factory.getInputNode(inputValues[i]));
        }

        for (int i = inputNodesNo; i < basicNodesNo + inputNodesNo; i++) {
            allNodes.add(factory.getNode());
        }
        for (int i=basicNodesNo + inputNodesNo; i< basicNodesNo + inputNodesNo + outputNodesNo; i++) {
            allNodes.add(factory.getOutputNode());
        }

        allNodes = mutator.mutateConnections(allNodes);

    }

    public void setNodes(List<Node<T>> nodes) {
        this.allNodes = nodes;
    }

    @Override
    public double evaluate() {


        return 0;
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
        for (Node n: allNodes){
            ConnectionAdapter<T> a = n.getAdapter();

            StringBuilder stringBuilder = new StringBuilder();
            List<Node> nodes = a.getNodes();
            for (int i = 0; i < nodes.size(); i++) {

                stringBuilder.append(nodes.get(i).getUID() + ", ");

            }
            System.out.println( n.getClass()+ " " + n.getUID() + " -> [" + stringBuilder.toString() + "]");
        }
    }

    @Override
    public IIndividual clone() {
        Individual ind = new Individual(this.nodeNo, this.params, this.factory, inputValues);
        List<Node<T>> copy = new ArrayList<>();

        for (int i = 0; i < allNodes.size(); i++) {
            copy.add(allNodes.get(i).clone());
        }

        // Now let's create new adapters and recreate them basing on original ones
        for (int i = 0; i < this.allNodes.size(); i++) {
            Node<T> currentNode = this.allNodes.get(i);
            List<Node> inputs = currentNode.getAdapter().getNodes();
            ConnectionAdapter adapter = new ConnectionAdapter(currentNode.getAdapter().getMaxArity());
            List<Node> newInputs = new ArrayList<>();

            for (Node<T> id : inputs) {
                if (id == null) {
                    //Basically means that no node is connected there
                    continue;
                }
                Node<T> foundNode = getNodeWithUID(copy, id.getUID());
                if (foundNode == null) {
                    System.err.println("Severe error!!");
                    continue;
                }
                newInputs.add(foundNode);
            }
            adapter.setInputs(newInputs);
           copy.get(i).setAdapter(adapter);

        }
        ind.setNodes(copy);

        return ind;
    }

    private Node<T> getNodeWithUID(List<Node<T>> nodes, int uid) {
        for (Node<T> node : nodes) {

            int aUID = node.getUID();
            if (aUID == uid) {
                return node;
            }

        }

        return null;
    }

}
