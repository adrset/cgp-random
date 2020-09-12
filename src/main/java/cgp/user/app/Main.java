package cgp.user.app;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.pojo.InputSamples;
import cgp.lib.simulation.SimulationModel;
import cgp.user.function.factory.RandomDoubleFunctionFactory;
import cgp.user.simulation.Evaluator;
import cgp.user.simulation.input.InputParams;

public class Main {

    private InputParams params;

    private SimulationModel<Double> simulation;

    public Main() throws Exception {
        this.params = InputParams.getInstance();
        FunctionFactory<Double> factory = new RandomDoubleFunctionFactory();
        InputSamples<Double> inputSamples = new InputSamples.Builder<Double>().setTargetClass(Double.class).setFileName("fib.json").build();

        //load samples

        this.simulation = new SimulationModel<>(params, factory, 0.1, new Evaluator(inputSamples.getSamples()));
        this.simulation.init();
    }

    public void run() {
        this.simulation.run();
    }

    public static void main(String[] args) throws Exception {
        new Main().run();
    }
}
