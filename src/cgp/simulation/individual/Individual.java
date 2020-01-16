package cgp.simulation.individual;

import cgp.simulation.node.INode;
import cgp.simulation.node.Node;
import cgp.simulation.node.adapter.ConnectionAdapter;
import cgp.simulation.node.factory.AbstractNodeFactory;
import cgp.simulation.input.InputParams;

import java.util.ArrayList;
import java.util.List;

public class Individual implements IIndividual{
    private int columns;
    private int rows;
    private INode cartesian[][];
    private InputParams params;
    AbstractNodeFactory factory;
    public Individual(int columns, int rows, InputParams params, AbstractNodeFactory factory){
        this.columns = columns;
        this.rows = rows;
        this.inputs = new Node[params.getInputs()];
        this.outputs = new Node[params.getOutputs()];
        this.factory = factory;
        this.params = params;
    }
    private Node inputs[];
    private Node outputs[];

    @Override
    public void init(){
        this.cartesian = new Node[columns][rows];

        for (int i = 0; i < this.cartesian.length; i++) {
            for (int j = 0; j < this.cartesian[i].length; j++) {
                this.cartesian[i][j] = factory.getNode();
            }
        }
    }

    public void setNodes(INode nodes[][]){
        this.cartesian = nodes;
    }

    @Override
    public double evaluate() {
        for (int i = 0; i < this.cartesian.length; i++) {
            for (int j = 0; j < this.cartesian[i].length; j++) {
                System.out.println(cartesian[i][j].getStrategy().getClass());
            }
        }
        return 0;
    }

    @Override
    public IIndividual clone(){
        Individual ind = new Individual(this.columns, this.rows, this.params, this.factory);
        INode cartesianCopy[][] = new Node[this.columns][this.rows];
        for (int i = 0; i < this.cartesian.length; i++) {
            for (int j = 0; j < this.cartesian[i].length; j++) {
                 cartesianCopy[i][j] = (INode) this.cartesian[i][j].clone();
            }
        }
        // Now let's create new adapters and recreate them basing on original ones
        for (int i = 0; i < this.cartesian.length; i++) {
            for (int j = 0; j < this.cartesian[i].length; j++) {
                List<INode> inputs = this.cartesian[i][j].getAdapter().getNodes();
                ConnectionAdapter adapter = new ConnectionAdapter(this.cartesian[i][j].getAdapter().getMaxArity());
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
                this.cartesian[i][j].setAdapter(adapter);
            }
        }
        ind.setNodes(cartesianCopy);

        return ind;
    }

    private static INode getNodeWithUID(INode nodes[][],int uid) {
        for (int i = 0; i < nodes.length; i++) {
            for (int j = 0; j < nodes[i].length; j++) {
                int aUID = nodes[i][j].getUID();
                if (aUID == uid) {
                    return nodes[i][j];
                }
            }
        }

        return null;
    }

}
