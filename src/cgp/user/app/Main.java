package cgp.user.app;

import cgp.lib.function.factory.FunctionFactory;
import cgp.user.function.factory.RandomDoubleFunctionFactory;
import cgp.user.simulation.input.InputParams;
import cgp.lib.simulation.SimulationModel;

public class Main {

    private InputParams params;

    private SimulationModel<Double> simulation;
    public Main(){
        this.params = InputParams.getInstance();
        FunctionFactory factory = new RandomDoubleFunctionFactory();
        Double[] in = {new Double(1), new Double(2)};
        this.simulation = new SimulationModel<>(params, factory, new Double(0.1), in);
        this.simulation.init();
    }

    public void run() {
        this.simulation.run();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
