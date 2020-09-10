package cgp.lib.simulation;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.samples.Sample;
import cgp.lib.node.factory.NodeFactory;
import cgp.lib.simulation.evaluation.IEvaluate;
import cgp.lib.simulation.mutator.function.FunctionMutator;
import cgp.lib.simulation.mutator.IMutator;
import cgp.lib.simulation.mutator.connection.InitialRandomConnectionMutator;
import cgp.lib.individual.Individual;
import cgp.user.simulation.input.InputParams;
import cgp.lib.simulation.mutator.connection.RandomConnectionMutator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationModel<T> {

    private int nodeAmount;
    Random generator;
    List<Individual<T>> individuals;
    private InputParams params;
    private FunctionFactory<T> factory;
    private NodeFactory<T> nodeFactory;
    private IMutator<T> connectionMutator;
    private IMutator<T> initialConnectionSetter;
    private FunctionMutator<T> functionMutator;
    private IEvaluate<T> evaluator;
    private T[] inputValues;

    public SimulationModel(InputParams params, FunctionFactory<T> factory, T defaultValue, T[] inputValues, IEvaluate<T> evaluator) {
        this.inputValues = inputValues;
        this.nodeAmount = params.getNodeAmount();
        this.generator = new Random();
        this.params = params;
        this.factory = factory;
        this.evaluator = evaluator;

        individuals = new ArrayList<>();
        connectionMutator = new RandomConnectionMutator<T>(params);
        initialConnectionSetter = new InitialRandomConnectionMutator<T>(params);
        functionMutator = new FunctionMutator<>(params, factory);
        nodeFactory = new NodeFactory<>(params, factory, defaultValue);
    }

    public void run() {
        try {
            // System.setOut(new PrintStream(new File("output-file.txt")));
            int currentGeneration = 0;

            while (currentGeneration++ < params.getGenerationThreshold()) {
                for (int ii = 0; ii < params.getIndividuals(); ii++) {
                    for (Sample<T> sample : evaluator.getSamples()) {
                        individuals.get(ii).compute(sample);
                        // get invidual value here and save it
                    }


                    evaluator.evaluate(individuals.get(ii));
                    mutate(individuals.get(ii));

                    //individuals[ii].describe();
                }
                System.out.println("==========" + (currentGeneration) + "==========");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mutate(Individual<T> i) {
        i.mutate(connectionMutator);
        i.mutate(functionMutator);
    }


    public double evaluate() {
        return 1;
    }

    public void init() {
        for (int ii = 0; ii < 1; ii++) {
            individuals.set(ii, new Individual<T>(this.nodeAmount, params, nodeFactory, inputValues));
            individuals.get(ii).init(initialConnectionSetter);
        }

        // Copy first individual
        for (int ii = 1; ii < params.getIndividuals(); ii++) {
            individuals.set(ii, individuals.get(0).clone());
        }

    }
}
