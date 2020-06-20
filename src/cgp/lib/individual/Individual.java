package cgp.lib.individual;

import cgp.user.simulation.input.InputParams;
import cgp.lib.node.Node;
import cgp.lib.node.OutputNode;
import cgp.lib.node.adapter.ConnectionAdapter;
import cgp.lib.node.factory.AbstractNodeFactory;
import cgp.lib.simulation.mutator.IMutator;

import java.util.ArrayList;
import java.util.List;

public class Individual<T>{
    private int nodeNo;
    private InputParams params;
    private AbstractNodeFactory<T> factory;
    private List<Node<T>> allNodes;
    private T[] inputValues;

    int basicNodesNo;
    int inputNodesNo;
    int outputNodesNo;

    public Individual(int nodeNo, InputParams params, AbstractNodeFactory<T> factory, T[] inputValues) {
        this.inputValues = inputValues;
        this.nodeNo = nodeNo;
        this.factory = factory;
        this.params = params;
        allNodes = new ArrayList<>();


    }

    public List<OutputNode<T>> getOutputNodes(){
        List<OutputNode<T>> outputs = new ArrayList<>();
        for (int i = basicNodesNo + inputNodesNo; i < basicNodesNo + inputNodesNo + outputNodesNo; i++) {
            outputs.add((OutputNode<T>)allNodes.get(i));
        }

        return outputs;
    }

    public void init(IMutator connectionMutator) {
        basicNodesNo = params.getNodeAmount();
        inputNodesNo = params.getInputs();
        outputNodesNo = params.getOutputs();
        for (int i = 0; i < inputNodesNo; i++) {
            allNodes.add(factory.getInputNode(inputValues[i]));
        }

        for (int i = inputNodesNo; i < basicNodesNo + inputNodesNo; i++) {
            allNodes.add(factory.getNode());
        }
        for (int i = basicNodesNo + inputNodesNo; i < basicNodesNo + inputNodesNo + outputNodesNo; i++) {
            allNodes.add(factory.getOutputNode());
        }

        allNodes = connectionMutator.mutate(allNodes);

    }

    public void setNodes(List<Node<T>> nodes) {
        this.allNodes = nodes;
    }

    public void compute() {
        resetNodesActiveStatus();
        setActiveNodes();
        for (int i = inputNodesNo; i < basicNodesNo + inputNodesNo; i++) {

            Node n = allNodes.get(i);
            if (n.isActive()) {
                allNodes.get(i).compute();
            }

        }

    }


    private void setActiveNodes() {

        for (int i = basicNodesNo + inputNodesNo; i < basicNodesNo + inputNodesNo + outputNodesNo; i++) {
            recursivelySetActiveNodes(i);
        }

    }

    private void resetNodesActiveStatus() {

        for (int i = 0; i < allNodes.size(); i++) {
            allNodes.get(i).setActive(false);
        }

    }

    private void recursivelySetActiveNodes(int index) {
        // not checking for input nodes
        if (index < inputNodesNo) {
            return;
        }

        Node<T> node = allNodes.get(index);

        if (node.isActive()) {
            return;
        }

        node.setActive(true);
        System.out.println("Node " + index + " is active!");

        ConnectionAdapter<T> adapter = node.getAdapter();
        for (Node<T> child : adapter.getNodes()) {
            int childIndex = child.getUID();
            recursivelySetActiveNodes(childIndex);
        }

    }


    public void mutate(IMutator mutator) {
        allNodes = mutator.mutate(allNodes);
    }

    public List<Node<T>> getAllNodes() {
        return allNodes;
    }

    public void describe() {
        for (Node n : allNodes) {
            ConnectionAdapter<T> a = n.getAdapter();

            StringBuilder stringBuilder = new StringBuilder();
            List<Node<T>> nodes = a.getNodes();
            for (int i = 0; i < nodes.size(); i++) {

                stringBuilder.append(nodes.get(i).getUID() + ", ");

            }
            System.out.println(n.getClass() + " " + n.getUID() + " -> [" + stringBuilder.toString() + "]");
        }


    }

    @Override
    public Individual clone() {
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
