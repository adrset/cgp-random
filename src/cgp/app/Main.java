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
        Double[] in = {new Double(1), new Double(2)};
        this.simulation = new SimulationModel<>(params, factory, new Double(0), in);
        this.simulation.init();
    }

    public void run() {
        this.simulation.run();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
