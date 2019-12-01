package cgp.simulation;

import cgp.nodes.Node;

public class SimulationModel {

    public Node cartesian[][];
    private int columns;
    private int rows;
    private int inputs;
    private int outputs;

    public SimulationModel(InputParams params) {
        this.columns = params.getColumns();
        this.rows    = params.getRows();

    }

    public void initCartesianGrid(){
        boolean NU[] = new boolean[columns*rows + inputs + outputs];
        this.cartesian = new Node[columns][rows];

        for (int i = 0; i < this.cartesian.length; i++) {
            for (int j = 0; j < this.cartesian[i].length; j++) {
                this.cartesian[i][j] = new Node();
            }
        }
    }

}
