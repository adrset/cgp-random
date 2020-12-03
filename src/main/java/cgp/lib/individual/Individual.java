package cgp.lib.individual;

import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.node.adapter.MemoryConnectionAdapter;
import cgp.lib.simulation.input.Config;
import cgp.lib.node.Node;
import cgp.lib.node.adapter.ConnectionAdapter;
import cgp.lib.node.factory.AbstractNodeFactory;
import cgp.lib.simulation.mutator.IMutator;

import java.util.ArrayList;
import java.util.List;

public class Individual<T> {
    private Config config;
    private AbstractNodeFactory<T> factory;
    private List<Node<T>> allNodes;
    private boolean parent = false;

    private int basicNodesNo;
    private int inputNodesNo;
    private int outputNodesNo;
    private double fitness;

    public boolean isParent() {
        return parent;
    }

    public void setParent(boolean parent) {
        this.parent = parent;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

    public Individual(Config config, AbstractNodeFactory<T> factory) {
        this.factory = factory;
        this.config = config;
        allNodes = new ArrayList<>();
    }

    public void zero() {
        T defaultVal = factory.getNode().getCurrentValue();
        for (int i = inputNodesNo; i < basicNodesNo + inputNodesNo; i++) {
            allNodes.get(i).setCurrentValue(defaultVal);
        }
    }

    /**
     * @param connectionMutator must be a connection mutator. Creates initial connections for all nodes.
     */
    public void init(IMutator<T> connectionMutator) {
        basicNodesNo = config.getNodeAmount();
        inputNodesNo = config.getInputs();
        outputNodesNo = config.getOutputs();
        for (int i = 0; i < inputNodesNo; i++) {
            allNodes.add(factory.getInputNode());
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
        setInputValues(sample);
        computeNodes();
        return getOutput();
    }

    private void computeNodes() {
        //TODO: iterate to otp nodes.
        for (int i = inputNodesNo; i < allNodes.size(); i++) {
            Node<T> n = allNodes.get(i);
            if (n.isActive()) {
                allNodes.get(i).compute();
            }

        }
    }

    private void setInputValues(Sample<T> s) {
        for (int i = 0; i < inputNodesNo; i++) {
            Node<T> n = allNodes.get(i);
            n.setCurrentValue(s.getInput().get(i));
        }
    }


    public List<T> getOutput() {

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
        // assuming one otp
        List<Node<T>> outputs = allNodes.get(allNodes.size() - 1).getAdapter().getNodes();
        for (Node<T> o : outputs) {
            recursivelySetActiveNodes(allNodes.indexOf(o));
        }

    }

    /**
     * Set all node statuses to inactive.
     */
    private void resetNodesActiveStatus() {

        for (Node<T> allNode : allNodes) {
            allNode.setActive(false);
        }

    }

    /**
     * Recursively finds nodes that have valid connections and marks them as active.
     *
     * @param index index of the node in the list
     */
    private void recursivelySetActiveNodes(int index) {
        recursivelySetActiveNodes(index, false);
    }

    private void recursivelySetActiveNodes(int index, boolean describe) {
        // not checking for input nodes
        if (index < inputNodesNo) {
            return;
        }

        Node<T> node = allNodes.get(index);

        if (node.isActive()) {
            return;
        }

        node.setActive(true);
        if (describe) {
            System.out.println(node.getStrategy().describe());

        }
        // TODO: IS ACTIVE
        //System.out.println("Node " + index + " is active!");

        ConnectionAdapter<T> adapter = node.getAdapter();
        for (Node<T> child : adapter.getNodes()) {
            int childIndex = allNodes.indexOf(child);
            recursivelySetActiveNodes(childIndex);
        }

    }


    /**
     * Mutates all nodes.
     *
     * @param mutator could be any user defined mutator, but initially meant for connection mutator and function mutator.
     */
    public void mutate(IMutator<T> mutator) {
        allNodes = mutator.mutate(allNodes);
    }


    public void describe() {

        for (Node<T> n : allNodes) {
            if (n.isActive()) {
                String inputString = "";
                List<Node<T>> nt = n.getAdapter().getNodes();
                for (Node<T> nti : nt) {
                    inputString += "(" + nti.getUID() + ")" + nti.getCurrentValue() + ", ";
                }
                inputString = inputString.substring(0, inputString.length() - 2);
                System.out.print(n.getStrategy().describe() + "[" + inputString + "], ");
            }
        }

    }

    @Override
    public Individual<T> clone() {
        Individual<T> ind = new Individual<>(this.config, this.factory);
        List<Node<T>> copy = new ArrayList<>();

        for (Node<T> allNode : allNodes) {
            copy.add(allNode.clone());
        }

        // Now let's create new adapters and recreate them basing on original ones
        for (int i = 0; i < this.allNodes.size(); i++) {
            Node<T> currentNode = this.allNodes.get(i);
            List<Node<T>> inputs = currentNode.getAdapter().getNodes();
            ConnectionAdapter<T> oldAdapter = currentNode.getAdapter();
            ConnectionAdapter<T> adapter = null;
            if (oldAdapter instanceof MemoryConnectionAdapter) {
                adapter = new MemoryConnectionAdapter<>(currentNode.getAdapter().getMaxArity());
                ((MemoryConnectionAdapter<T>) adapter).setInputLocations(new ArrayList<>(((MemoryConnectionAdapter<T>) oldAdapter).getInputLocations()));
            } else {
                adapter = new ConnectionAdapter<>(currentNode.getAdapter().getMaxArity());

            }
            List<Node<T>> newInputs = new ArrayList<>();

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
        ind.parent = false;

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

    public String toString(){
        return "" + fitness;
    }

}
