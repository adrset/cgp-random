package cgp.lib.individual;

import cgp.lib.individual.samples.Sample;
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

    /**
     *
     * @param connectionMutator must be a connection mutator. Creates initial connections for all nodes.
     */
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

        // One output node that has n outputs linked as inputs
        // basically just a helper node. :)
        allNodes.add(factory.getOutputNode());


        allNodes = connectionMutator.mutate(allNodes);

    }

    public void setNodes(List<Node<T>> nodes) {
        this.allNodes = nodes;
    }

    /**
     * Computes active nodes from left to right (firstly determines which are active using respective methods).
     */
    public List<T> compute(Sample<T> sample) {
        resetNodesActiveStatus();
        setActiveNodes();
        for (int i = inputNodesNo; i < basicNodesNo + inputNodesNo; i++) {

            Node n = allNodes.get(i);
            if (n.isActive()) {
                allNodes.get(i).compute();
            }

        }

        return getOutput();
    }


    public List<T> getOutput(){
        /**
         * Get values of nodes that are outputs.
         */

        List<T> values = new ArrayList<>();
        List<Node<T>> outputs = allNodes.get(allNodes.size() - 1).getAdapter().getNodes();
        for (Node<T> o : outputs) {
            values.add(o.getCurrentValue());
        }

        return values;
    }

    /**
     * Loops trough output nodes and recursively marks all nodes that it's connected to.
     */
    private void setActiveNodes() {
        List<Node<T>> outputs = allNodes.get(allNodes.size() - 1).getAdapter().getNodes();
        for (Node<T> o : outputs) {
            recursivelySetActiveNodes(allNodes.indexOf(o));
        }


    }

    /**
     * Set all node statuses to inactive.
     */
    private void resetNodesActiveStatus() {

        for (int i = 0; i < allNodes.size(); i++) {
            allNodes.get(i).setActive(false);
        }

    }

    /**
     * Recursively finds nodes that have valid connections and marks them as active.
     * @param index index of the node in the list
     */
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
        // TODO: IS ACTIVE
        //System.out.println("Node " + index + " is active!");

        ConnectionAdapter<T> adapter = node.getAdapter();
        for (Node<T> child : adapter.getNodes()) {
            int childIndex = child.getUID();
            recursivelySetActiveNodes(childIndex);
        }

    }


    /**
     * Mutates all nodes.
     * @param mutator could be any user defined mutator, but initially meant for connection mutator and function mutator.
     */
    public void mutate(IMutator mutator) {
        allNodes = mutator.mutate(allNodes);
    }

    public void describe() {
//        for (Node n : allNodes) {
//            ConnectionAdapter<T> a = n.getAdapter();
//
//            StringBuilder stringBuilder = new StringBuilder();
//            List<Node<T>> nodes = a.getNodes();
//            for (int i = 0; i < nodes.size(); i++) {
//
//                stringBuilder.append(nodes.get(i).getUID() + ", ");
//
//            }
//            System.out.println(n.getClass() + " " + n.getUID() + " -> [" + stringBuilder.toString() + "]");
//        }

        for (int j = allNodes.size() - 1; j >= allNodes.size() - outputNodesNo; j--) {
            Node<T> n = allNodes.get(j);
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

        ind.inputNodesNo = this.inputNodesNo;
        ind.basicNodesNo = this.basicNodesNo;
        ind.outputNodesNo = this.outputNodesNo;

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
