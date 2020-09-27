package cgp.user.app;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.Individual;
import cgp.lib.individual.pojo.InputSamples;
import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.simulation.SimulationModel;
import cgp.user.function.factory.RandomBooleanFunctionFactory;
import cgp.user.function.factory.RandomDoubleFunctionFactory;
import cgp.user.simulation.AdderEvaluator;
import cgp.user.simulation.Evaluator;
import cgp.user.simulation.input.InputParams;

import java.util.Arrays;
import java.util.List;

public class MainBoolean {

    private InputParams params;

    private SimulationModel<Boolean> simulation;

    public MainBoolean() throws Exception {
        this.params = InputParams.getInstance();

        FunctionFactory<Boolean> factory = new RandomBooleanFunctionFactory();
        InputSamples<Boolean> inputSamples = new InputSamples.Builder<Boolean>().setTargetClass(Boolean.class).setFileName("adder.json").build();
        params.setConfig(inputSamples.getConfig());
        //load samples

        this.simulation = new SimulationModel<>(params, factory, false, new AdderEvaluator(inputSamples.getSamples()), SimulationModel.Mode.CGP);
        this.simulation.init();
    }

    public void run() {
        this.simulation.run();
        Individual<Boolean> theFittest = this.simulation.getFittest();
        Sample<Boolean> s = new Sample<>();

        s.setInput(Arrays.asList(true, false, false));

        List<Boolean> otp = theFittest.compute(s);
        System.out.println(otp);
        theFittest.describe();


    }

    public static void main(String[] args) throws Exception {
        new MainBoolean().run();
    }
}
