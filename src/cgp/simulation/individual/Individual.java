package cgp.simulation.individual;

import cgp.simulation.node.INode;
import cgp.simulation.node.Node;
import cgp.simulation.node.adapter.ConnectionAdapter;
import cgp.simulation.node.factory.AbstractNodeFactory;
import cgp.simulation.input.InputParams;

import java.util.ArrayList;
import java.util.List;

public class Individual implements IIndividual{
    private int nodeNo;
    private INode cartesian[];
    private InputParams params;
    AbstractNodeFactory factory;
    public Individual(int nodeNo, InputParams params, AbstractNodeFactory factory){
        this.nodeNo = nodeNo;
        this.inputs = new Node[params.getInputs()];
        this.outputs = new Node[params.getOutputs()];
        this.factory = factory;
        this.params = params;
    }
    private Node inputs[];
    private Node outputs[];

    @Override
    public void init(){
        this.cartesian = new Node[nodeNo];

        for (int i = 0; i < this.cartesian.length; i++) {

        }
    }

    public void setNodes(INode nodes[]){
        this.cartesian = nodes;
    }

    @Override
    public double evaluate() {
        for (int i = 0; i < this.cartesian.length; i++) {

            System.out.println(cartesian[i].getStrategy().getClass());

        }
        return 0;
    }

    @Override
    public IIndividual clone(){
        Individual ind = new Individual(this.nodeNo, this.params, this.factory);
        INode cartesianCopy[] = new Node[this.nodeNo];
        for (int i = 0; i < this.cartesian.length; i++) {
             cartesianCopy[i] = (INode) this.cartesian[i].clone();

        }
        // Now let's create new adapters and recreate them basing on original ones
        for (int i = 0; i < this.cartesian.length; i++) {

            List<INode> inputs = this.cartesian[i].getAdapter().getNodes();
            ConnectionAdapter adapter = new ConnectionAdapter(this.cartesian[i].getAdapter().getMaxArity());
            List<INode> newInputs = new ArrayList<>();

            for (INode id: inputs) {
                if(id == null) {
                    //Basically means that no node is connected there
                    continue;
                }
                INode foundNode = getNodeWithUID( this.cartesian,id.getUID());
                if (foundNode == null){
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

    private static INode getNodeWithUID(INode nodes[],int uid) {
        for (int i = 0; i < nodes.length; i++) {

            int aUID = nodes[i].getUID();
            if (aUID == uid) {
                return nodes[i];
            }

        }

        return null;
    }

}
