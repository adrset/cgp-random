package cgp.user.app;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.samples.Sample;
import cgp.user.function.factory.RandomDoubleFunctionFactory;
import cgp.user.simulation.Evaluator;
import cgp.user.simulation.input.InputParams;
import cgp.lib.simulation.SimulationModel;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private InputParams params;

    private SimulationModel<Double> simulation;
    public Main(){
        this.params = InputParams.getInstance();
        FunctionFactory<Double> factory = new RandomDoubleFunctionFactory();
        Double[] in = {1d, 2d};
        List<Sample<Double>> samples = new ArrayList<>();

        //load samples

        this.simulation = new SimulationModel<>(params, factory, 0.1, in, new Evaluator<>(samples));
        this.simulation.init();
    }

    public void run() {
        this.simulation.run();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
