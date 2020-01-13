package cgp.simulation;

import cgp.function.factory.IFunctionFactory;
import cgp.node.Node;
import cgp.node.factory.RandomNodeFactory;
import cgp.simulation.input.InputParams;

import java.util.Random;

public class SimulationModel implements ISimulation{

    public Node cartesian[][];
    private int columns;
    private int rows;
    Random generator;
    private Node inputs[];
    private Node outputs[];
    private InputParams params;
    private IFunctionFactory factory;
    private RandomNodeFactory nodeFactory;

    public SimulationModel(InputParams params) {
        this.columns = params.getColumns();
        this.rows    = params.getRows();
        this.inputs = new Node[params.getInputs()];
        this.outputs = new Node[params.getOutputs()];
        this.generator = new Random();
        this.params = params;
        nodeFactory = new RandomNodeFactory(params);
    }

    @Override
    public void run() {
        for (int i = 0; i < this.cartesian.length; i++) {
            for (int j = 0; j < this.cartesian[i].length; j++) {
                System.out.println(cartesian[i][j].getStrategy().getClass());
            }
        }
    }

    @Override
    public void evaluate() {

    }

    @Override
    public void init(){
        boolean NU[] = new boolean[columns*rows];
        this.cartesian = new Node[columns][rows];

        for (int i = 0; i < this.cartesian.length; i++) {
            for (int j = 0; j < this.cartesian[i].length; j++) {
                this.cartesian[i][j] = nodeFactory.getNode();
            }
        }
    }
}
