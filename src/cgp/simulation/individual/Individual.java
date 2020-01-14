package cgp.simulation.individual;

import cgp.node.Node;
import cgp.node.factory.AbstractNodeFactory;
import cgp.simulation.input.InputParams;

public class Individual implements IIndividual{
    private int columns;
    private int rows;
    private Node cartesian[][];
    private InputParams params;
    AbstractNodeFactory factory;
    public Individual(int columns, int rows, InputParams params, AbstractNodeFactory factory){
        this.columns = columns;
        this.rows = rows;
        this.inputs = new Node[params.getInputs()];
        this.outputs = new Node[params.getOutputs()];
        this.factory = factory;
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

    @Override
    public double evaluate() {
        for (int i = 0; i < this.cartesian.length; i++) {
            for (int j = 0; j < this.cartesian[i].length; j++) {
                System.out.println(cartesian[i][j].getStrategy().getClass());
            }
        }
        return 0;
    }
}
