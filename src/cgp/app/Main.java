package cgp.app;

import cgp.function.factory.FunctionFactory;
import cgp.function.factory.RandomDoubleFunctionFactory;
import cgp.simulation.ISimulation;
import cgp.input.InputParams;
import cgp.simulation.SimulationModel;

public class Main {

    private InputParams params;

    private ISimulation<Double> simulation;
    public Main(){
        this.params = InputParams.getInstance();
        FunctionFactory factory = new RandomDoubleFunctionFactory();
        this.simulation = new SimulationModel<>(params, factory);
        this.simulation.init();
    }

    public void run() {
        this.simulation.run();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
