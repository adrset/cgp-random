package cgp.lib.simulation;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.pojo.samples.Sample;
import cgp.lib.node.factory.NodeFactory;
import cgp.lib.node.guard.ComputedValueGuard;
import cgp.lib.simulation.evaluation.AbstractEvaluate;
import cgp.lib.simulation.input.Config;
import cgp.lib.simulation.mutator.connection.resursive.InitialRecursiveRandomConnectionMutator;
import cgp.lib.simulation.mutator.connection.resursive.RecursiveRandomConnectionMutator;
import cgp.lib.simulation.mutator.function.FunctionMutator;
import cgp.lib.simulation.mutator.IMutator;
import cgp.lib.simulation.mutator.connection.InitialRandomConnectionMutator;
import cgp.lib.individual.Individual;
import cgp.lib.simulation.mutator.connection.RandomConnectionMutator;
import cgp.lib.threading.ThreadPoolService;
import cgp.user.simulation.Evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;


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
    private ComputedValueGuard<T> guard;
    private List<IndividualCompute<T>> computors;
    private ThreadPoolService service;

    public enum Mode {CGP, RCGP}

    private Mode mode;

    public SimulationModel(Config config, FunctionFactory<T> factory, T defaultValue, AbstractEvaluate<T> evaluator, ComputedValueGuard<T> guard, Mode mode) {
        this.generator = new Random();
        this.config = config;
        this.factory = factory;
        this.evaluator = evaluator;
        this.guard = guard;
        this.mode = mode;
        this.service = new ThreadPoolService(config.getIndividuals());

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
        computors = new ArrayList<>();

    }

    public SimulationModel(Config config, FunctionFactory<T> factory, T defaultValue, AbstractEvaluate<T> evaluator, ComputedValueGuard<T> guard) {
        this(config, factory, defaultValue, evaluator, guard, Mode.CGP);
    }

    public void run() {
        int currentGeneration = 0;
        int printEvery = 1000;
        double fitness;

        do {

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
        for (Sample<T> sample : evaluator.getSamples()) {
            List<T> output = theFittest.compute(sample);
            String tmp = "[" + String.join(",", output.stream().map(e -> String.format("%.0f", e)).collect(Collectors.toList())) + "]";
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

        System.out.println("Initialising " + config.getIndividuals() + " workers.");
        for (int i = 0; i < config.getIndividuals(); i++) {
            computors.add(new IndividualCompute<>(guard, evaluator));
        }

    }

    private void computeIndividuals() {
        service.init(individuals.size());
        for (int ii = 0; ii < individuals.size(); ii++) {
            Individual<T> individual = individuals.get(ii);
            //individual.zero();
            for (Sample<T> sample : evaluator.getSamples()) {
                List<T> values = individual.compute(sample);
                if (guard != null) {
                    values = values.stream().map(e ->
                            guard.guard(e)).collect(Collectors.toList());
                }

                sample.setComputedOutput(values);

            }
            individual.setFitness(evaluator.evaluate());
        }
    }

    private static class IndividualCompute<T> implements Runnable {
        private Individual<T> individual;
        private AbstractEvaluate<T> evaluator;
        private ComputedValueGuard<T> guard;
        private CountDownLatch latch;
        IndividualCompute(ComputedValueGuard<T> guard, AbstractEvaluate<T> evaluator) {
            this.guard = guard;
            this.evaluator = evaluator;

        }

        void setVariables(Individual<T> individual, CountDownLatch latch){
            this.individual = individual;
            this.latch = latch;
        }



        @Override
        public void run() {
            for (Sample<T> sample : evaluator.getSamples()) {
                List<T> values = individual.compute(sample);
                if (guard != null) {
                    values = values.stream().map(e ->
                            guard.guard(e)).collect(Collectors.toList());
                }

                sample.setComputedOutput(values);

            }
            individual.setFitness(evaluator.evaluate());
            latch.countDown();
        }
    };


}
