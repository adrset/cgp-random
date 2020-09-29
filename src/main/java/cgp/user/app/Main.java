package cgp.user.app;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.Individual;
import cgp.lib.individual.pojo.InputSamples;
import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.simulation.SimulationModel;
import cgp.user.function.factory.RandomDoubleFunctionFactory;
import cgp.user.simulation.Evaluator;
import cgp.user.simulation.input.InputParams;

import java.util.Arrays;
import java.util.List;

public class Main {

    private InputParams params;

    private SimulationModel<Double> simulation;

    public Main() throws Exception {
        this.params = InputParams.getInstance();
        FunctionFactory<Double> factory = new RandomDoubleFunctionFactory();
        InputSamples<Double> inputSamples = new InputSamples.Builder<Double>().setTargetClass(Double.class).setFileName("sq2.json").build();
        params.setConfig(inputSamples.getConfig());
        //load samples

        this.simulation = new SimulationModel<>(params, factory, 0., new Evaluator(inputSamples.getSamples()), SimulationModel.Mode.CGP);
        this.simulation.init();
    }

    public void run() {
        this.simulation.run();
        Individual<Double> theFittest = this.simulation.getFittest();
        Sample<Double> s = new Sample<>();
        s.setInput(Arrays.asList(77d));

        List<Double> otp = theFittest.compute(s);
        System.out.println(otp);
        theFittest.describe();


    }

    public static void main(String[] args) throws Exception {
        new Main().run();
    }
}
