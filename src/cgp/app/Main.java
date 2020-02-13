package cgp.app;

import cgp.simulation.ISimulation;
import cgp.input.InputParams;
import cgp.simulation.SimulationModel;

public class Main {

    private InputParams params;

    private ISimulation simulation;
    public Main(){
        this.params = InputParams.getInstance();
        this.simulation = new SimulationModel(params);
        this.simulation.init();
    }

    public void run() {
        this.simulation.run();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
