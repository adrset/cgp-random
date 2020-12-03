package cgp.user.app;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.Individual;
import cgp.lib.individual.pojo.InputSamples;
import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.node.factory.NodeFactory;
import cgp.lib.simulation.SimulationModel;
import cgp.user.function.factory.RandomBooleanFunctionFactory;
import cgp.user.simulation.AdderEvaluator;

import java.util.Arrays;
import java.util.List;

public class MainBoolean {


    private SimulationModel<Boolean> simulation;

    public MainBoolean() throws Exception {

        FunctionFactory<Boolean> factory = new RandomBooleanFunctionFactory();
        InputSamples<Boolean> inputSamples = new InputSamples.Builder<Boolean>().setTargetClass(Boolean.class).setFileName("adder.json").build();
        NodeFactory<Boolean> nodeFactory = new NodeFactory<>(inputSamples.getConfig(), factory, false);


        this.simulation = new SimulationModel<>(inputSamples.getConfig(), nodeFactory, new AdderEvaluator(inputSamples.getSamples()), null, SimulationModel.Mode.CGP);
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
