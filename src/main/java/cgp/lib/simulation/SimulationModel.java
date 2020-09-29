package cgp.lib.simulation;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.node.factory.NodeFactory;
import cgp.lib.simulation.evaluation.AbstractEvaluate;
import cgp.lib.simulation.mutator.connection.resursive.InitialRecursiveRandomConnectionMutator;
import cgp.lib.simulation.mutator.connection.resursive.RecursiveRandomConnectionMutator;
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

    Random generator;
    List<Individual<T>> individuals;

    private InputParams params;
    private FunctionFactory<T> factory;
    private NodeFactory<T> nodeFactory;
    private IMutator<T> connectionMutator;
    private IMutator<T> initialConnectionSetter;
    private FunctionMutator<T> functionMutator;
    private AbstractEvaluate<T> evaluator;
    Individual<T> theFittest = null;

    public static enum Mode {CGP, RCGP}

    ;
    Mode mode;

    public SimulationModel(InputParams params, FunctionFactory<T> factory, T defaultValue, AbstractEvaluate<T> evaluator, Mode mode) {
        this.generator = new Random();
        this.params = params;
        this.factory = factory;
        this.evaluator = evaluator;
        this.mode = mode;

        individuals = new ArrayList<>();
        if (mode == Mode.CGP) {
            connectionMutator = new RandomConnectionMutator<T>(params);
            initialConnectionSetter = new InitialRandomConnectionMutator<T>(params);
        } else {
            connectionMutator = new RecursiveRandomConnectionMutator<>(params);
            initialConnectionSetter = new InitialRecursiveRandomConnectionMutator<>(params);

        }
        functionMutator = new FunctionMutator<>(params, factory);
        nodeFactory = new NodeFactory<>(params, factory, defaultValue);
    }

    public SimulationModel(InputParams params, FunctionFactory<T> factory, T defaultValue, AbstractEvaluate<T> evaluator) {
        this(params, factory, defaultValue, evaluator, Mode.CGP);
    }

    public void run() {
        try {

            int currentGeneration = 0;
            double fitness;
            while (currentGeneration++ < params.getGenerationThreshold()) {
                //evaluacja
                for (int ii = 0; ii < params.getIndividuals(); ii++) {
                    Individual<T> individual = individuals.get(ii);
                    for (Sample<T> sample : evaluator.getSamples()) {
                        //zbieraj odp.
                        sample.setComputedOutput(individual.compute(sample));
                    }
                    individual.setFitness(evaluator.evaluate());
                }
                theFittest = evaluator.getFittest(individuals);
                List<Individual<T>> newIndividuals = new ArrayList<>();
                newIndividuals.add(theFittest);
                for (int i = 0; i < params.getIndividuals() - 1; i++) {
                    newIndividuals.add(theFittest.clone());
                }
                individuals = newIndividuals;
                for (int i = 1; i < params.getIndividuals(); i++) {
                    mutate(individuals.get(i));
                }
                fitness = theFittest.getFitness();

                if (fitness < params.getMinError()) {
                    System.out.println("Min error reached! [g:" + currentGeneration + "]");
                    break;
                }

            }
            for (Sample<T> sample : evaluator.getSamples()) {
                //zbieraj odp.
                System.out.println(theFittest.compute(sample));
            }
            System.out.println(theFittest.getFitness());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Individual<T> getFittest() {
        return theFittest;
    }


    public void mutate(Individual<T> i) {
        i.mutate(connectionMutator);
        i.mutate(functionMutator);
    }

    public void init() {
        System.out.println("Creating " + params.getIndividuals() + " individuals.");
        for (int ii = 0; ii < params.getIndividuals(); ii++) {
            Individual<T> individual = new Individual<>(params, nodeFactory);
            individual.init(initialConnectionSetter);
            individual.setParent(true);
            individuals.add(individual);
        }

    }
}
