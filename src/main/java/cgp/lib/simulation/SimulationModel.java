package cgp.lib.simulation;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.node.factory.NodeFactory;
import cgp.lib.node.guard.ComputedValueGuard;
import cgp.lib.simulation.evaluation.AbstractEvaluate;
import cgp.lib.simulation.input.Config;
import cgp.lib.simulation.mutator.connection.memory.InitialRandomMemoryConnectionMutator;
import cgp.lib.simulation.mutator.connection.memory.RandomMemoryConnectionMutator;
import cgp.lib.simulation.mutator.connection.resursive.InitialRecursiveRandomConnectionMutator;
import cgp.lib.simulation.mutator.connection.resursive.RecursiveRandomConnectionMutator;
import cgp.lib.simulation.mutator.function.FunctionMutator;
import cgp.lib.simulation.mutator.IMutator;
import cgp.lib.simulation.mutator.connection.InitialRandomConnectionMutator;
import cgp.lib.individual.Individual;
import cgp.lib.simulation.mutator.connection.RandomConnectionMutator;
import cgp.lib.threading.ThreadPoolService;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


public class SimulationModel<T> {

    private Random generator;
    private List<Individual<T>> individuals;

    private Config config;
    private NodeFactory<T> nodeFactory;
    private IMutator<T> connectionMutator;
    private IMutator<T> initialConnectionSetter;
    private FunctionMutator<T> functionMutator;
    private AbstractEvaluate<T> evaluator;
    private Individual<T> theFittest = null;
    private ComputedValueGuard<T> guard;
    private ThreadPoolService service;

    public enum Mode {CGP, RCGP, MCGP}

    private Mode mode;

    public SimulationModel(Config config, NodeFactory<T> factory, AbstractEvaluate<T> evaluator, ComputedValueGuard<T> guard, Mode mode) {
        this.generator = new Random();
        this.config = config;
        this.nodeFactory = factory;
        this.evaluator = evaluator;
        this.guard = guard;
        this.mode = mode;
        this.service = new ThreadPoolService(config.getIndividuals());

        individuals = new ArrayList<>();
        if (mode == Mode.CGP) {
            connectionMutator = new RandomConnectionMutator<>(config);
            initialConnectionSetter = new InitialRandomConnectionMutator<>(config);
        } else if (mode == Mode.RCGP){
            connectionMutator = new RecursiveRandomConnectionMutator<>(config);
            initialConnectionSetter = new InitialRecursiveRandomConnectionMutator<>(config);
        } else if (mode == Mode.MCGP){
            connectionMutator = new RandomMemoryConnectionMutator<>(config);
            initialConnectionSetter = new InitialRandomMemoryConnectionMutator<>(config);
        }
        functionMutator = new FunctionMutator<>(config, nodeFactory.getFunctionFactory());

    }

    public SimulationModel(Config config, NodeFactory<T> factory, AbstractEvaluate<T> evaluator, ComputedValueGuard<T> guard) {
        this(config, factory, evaluator, guard, Mode.CGP);
    }

    public void run() {
        int currentGeneration = 0;
        int printEvery = 1000;
        double fitness;

        do {
            System.out.println("gen " + currentGeneration);
            computeIndividuals();

            theFittest = evaluator.getFittest(individuals);
            fitness = theFittest.getFitness();

            makeOffspring();
            mutateGeneration();

            if (currentGeneration % printEvery == 0) {
                System.out.println("\t" + theFittest.getFitness());
            }

            if (fitness < config.getMinError()) {
                System.out.println("Min error reached! [g:" + currentGeneration + "]");
                break;
            }


        } while (currentGeneration++ < config.getGenerationThreshold());

        log();
    }


    private void log() {
        theFittest.zero();
        for (Sample<T> sample : evaluator.getSamples()) {
            List<T> output = theFittest.compute(sample);
            String tmp = "[" + output.stream().map(e -> String.format("%.0f", e)).collect(Collectors.joining(",")) + "]";
            System.out.println(tmp);

        }
        System.out.println(theFittest.getFitness());
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

    private void computeIndividuals() {
        service.init(individuals.size());
        individuals.stream().peek(individual -> {
            individual.zero();
            for (Sample<T> sample : evaluator.getSamples()) {
                // do not store output in sample!!!!
                List<T> values = individual.compute(sample);
                if (guard != null) {
                    values = values.stream().map(e ->
                            guard.guard(e)).collect(Collectors.toList());
                }

                sample.setComputedOutput(values);

            }
            individual.setFitness(evaluator.evaluate());
        }).collect(Collectors.toList());
    }


}
