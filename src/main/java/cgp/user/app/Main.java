package cgp.user.app;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.Individual;
import cgp.lib.individual.pojo.InputSamples;
import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.node.factory.NodeFactory;
import cgp.lib.simulation.SimulationModel;
import cgp.user.function.factory.RandomDoubleFunctionFactory;
import cgp.user.node.guard.DoubleGuard;
import cgp.user.simulation.FibEvaluator;

import java.util.Arrays;
import java.util.List;

public class Main {

    private SimulationModel<Double> simulation;

    public Main() throws Exception {
        FunctionFactory<Double> factory = new RandomDoubleFunctionFactory();
        InputSamples<Double> inputSamples = new InputSamples.Builder<Double>().setTargetClass(Double.class).setFileName("fib.json").build();
        NodeFactory<Double> nodeFactory = new NodeFactory<>(inputSamples.getConfig(), factory, 0.);
        this.simulation = new SimulationModel<>(inputSamples.getConfig(), nodeFactory, new FibEvaluator(inputSamples.getSamples()), new DoubleGuard(), SimulationModel.Mode.RCGP);
        this.simulation.init();
    }

    public void run() {
        this.simulation.run();
        Individual<Double> theFittest = this.simulation.getFittest();
        Sample<Double> s = new Sample<>();
        s.setInput(Arrays.asList(1d));
        System.out.println("kolejne wyrazy");
        double before = 24157817;
        double lastVal = 39088169d;
        double tmp;
        for (int i = 0; i < 30; i++) {
            List<Double> otp = theFittest.compute(s);
            System.out.printf("[%.0f] vs %.0f\n", otp.get(0), (before + lastVal));
            tmp = lastVal + before;
            before = lastVal;
            lastVal = tmp;

        }


        theFittest.describe();


    }

    public static void main(String[] args) throws Exception {
        new Main().run();
    }
}
