package cgp.lib.simulation;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.node.factory.NodeFactory;
import cgp.lib.simulation.evaluation.AbstractEvaluate;
import cgp.lib.simulation.input.Config;
import cgp.lib.simulation.mutator.connection.resursive.InitialRecursiveRandomConnectionMutator;
import cgp.lib.simulation.mutator.connection.resursive.RecursiveRandomConnectionMutator;
import cgp.lib.simulation.mutator.function.FunctionMutator;
import cgp.lib.simulation.mutator.IMutator;
import cgp.lib.simulation.mutator.connection.InitialRandomConnectionMutator;
import cgp.lib.individual.Individual;
import cgp.lib.simulation.mutator.connection.RandomConnectionMutator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Double.NaN;

public class SimulationModel<T> {

    private Random generator;
    private List<Individual<T>> individuals;

    private Config config;
    private FunctionFactory<T> factory;
    private NodeFactory<T> nodeFactory;
    private IMutator<T> connectionMutator;
    private IMutator<T> initialConnectionSetter;
    private FunctionMutator<T> functionMutator;
    private AbstractEvaluate<T> evaluator;
    private Individual<T> theFittest = null;

    public enum Mode {CGP, RCGP}

    private Mode mode;

    public SimulationModel(Config config, FunctionFactory<T> factory, T defaultValue, AbstractEvaluate<T> evaluator, Mode mode) {
        this.generator = new Random();
        this.config = config;
        this.factory = factory;
        this.evaluator = evaluator;
        this.mode = mode;

        individuals = new ArrayList<>();
        if (mode == Mode.CGP) {
            connectionMutator = new RandomConnectionMutator<>(config);
            initialConnectionSetter = new InitialRandomConnectionMutator<>(config);
        } else {
            connectionMutator = new RecursiveRandomConnectionMutator<>(config);
            initialConnectionSetter = new InitialRecursiveRandomConnectionMutator<>(config);

        }
        functionMutator = new FunctionMutator<>(config, factory);
        nodeFactory = new NodeFactory<>(config, factory, defaultValue);
    }

    public SimulationModel(Config config, FunctionFactory<T> factory, T defaultValue, AbstractEvaluate<T> evaluator) {
        this(config, factory, defaultValue, evaluator, Mode.CGP);
    }

    public void run() {
        int currentGeneration = 0;
        int printEvery = 1000;
        double fitness;
        double oldFittness = Double.MAX_VALUE;
        Individual<T> elder = null;
        do {

            computeIndividuals();

            theFittest = evaluator.getFittest(individuals);
            fitness = theFittest.getFitness();
//            if (oldFittness < fitness) {
//                System.out.println("err");
//                for (int i =0; i<10;i++){
//                    computeIndividuals();
//
//                    theFittest = evaluator.getFittest(individuals);
//                    fitness = theFittest.getFitness();
//                    System.out.println("iter: " + i );
//                    theFittest.describe();
//                }
//            }
//            if (Double.isNaN(fitness)){
//                theFittest.describe();
//
//            }
            makeOffspring();
            mutateGeneration();

            if (currentGeneration % printEvery == 0) {
                System.out.println("\t" + theFittest.getFitness());
            }

            if (fitness < config.getMinError()) {
                System.out.println("Min error reached! [g:" + currentGeneration + "]");
                break;
            }

            oldFittness = theFittest.getFitness();
            elder = theFittest;

        } while (currentGeneration++ < config.getGenerationThreshold());
        log();

    }


    private void log() {
        for (Sample<T> sample : evaluator.getSamples()) {
            System.out.println(theFittest.compute(sample));
        }
        System.out.println(theFittest.getFitness());
    }


    private void computeIndividuals() {
        for (int ii = 0; ii < individuals.size(); ii++) {
            Individual<T> individual = individuals.get(ii);
            for (Sample<T> sample : evaluator.getSamples()) {
                //zbieraj odp.
                sample.setComputedOutput(individual.compute(sample));
            }
            individual.setFitness(evaluator.evaluate());
        }
    }


    private void makeOffspring() {
        List<Individual<T>> newIndividuals = new ArrayList<>();
        newIndividuals.add(theFittest);
        for (int i = 0; i < config.getIndividuals() - 1; i++) {
            Individual<T> child = theFittest.clone();
            child.setFitness(theFittest.getFitness());
            newIndividuals.add(child);
        }

        individuals = newIndividuals;
    }

    private void mutateGeneration() {
        for (int i = 0; i < config.getIndividuals(); i++) {
            if (!individuals.get(i).isParent()) {
                mutate(individuals.get(i));
            }
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
        System.out.println("Creating " + config.getIndividuals() + " individuals.");
        for (int ii = 0; ii < config.getIndividuals(); ii++) {
            Individual<T> individual = new Individual<>(config, nodeFactory);
            individual.init(initialConnectionSetter);
            individuals.add(individual);
        }

    }
}
