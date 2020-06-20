package cgp.lib.simulation;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.node.factory.NodeFactory;
import cgp.lib.simulation.mutator.function.FunctionMutator;
import cgp.lib.simulation.mutator.IMutator;
import cgp.lib.simulation.mutator.connection.InitialRandomConnectionMutator;
import cgp.lib.individual.Individual;
import cgp.user.simulation.input.InputParams;
import cgp.lib.simulation.mutator.connection.RandomConnectionMutator;

import java.util.Random;

public class SimulationModel<T>{

    private int nodeAmount;
    Random generator;
    Individual<T> individuals[];
    private InputParams params;
    private FunctionFactory factory;
    private NodeFactory nodeFactory;
    private IMutator connectionMutator;
    private IMutator initialConnectionSetter;
    private IMutator functionMutator;
    private T[] inputValues;

    public SimulationModel(InputParams params, FunctionFactory<T> factory, T defaultValue, T[] inputValues) {
        this.inputValues = inputValues;
        this.nodeAmount = params.getNodeAmount();
        this.generator = new Random();
        this.params = params;
        this.factory = factory;

        individuals = new Individual[params.getIndividuals()];
        connectionMutator = new RandomConnectionMutator<T>(params);
        initialConnectionSetter = new InitialRandomConnectionMutator<T>(params);
        functionMutator = new FunctionMutator(params, factory);
        nodeFactory = new NodeFactory<T>(params, factory, defaultValue);
    }

    public void run() {
        try {
            // System.setOut(new PrintStream(new File("output-file.txt")));


            int currentGeneration = 0;
            while (currentGeneration++ <= 3/*params.getGenerationThreshold() && evaluate() > params.getMinError()*/) {
                for (int ii = 0; ii < 4; ii++) {
                    individuals[ii].compute();
                    System.out.println("-" + (ii + 1) + "-");
                    individuals[ii].mutate(connectionMutator);
                    individuals[ii].mutate(functionMutator);

                    //individuals[ii].describe();
                }
                System.out.println("==========" + (currentGeneration) + "=========");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double evaluate() {
        return 1;
    }

    public void init() {
        for (int ii = 0; ii < 1; ii++) {
            individuals[ii] = new Individual<T>(this.nodeAmount, params, nodeFactory, inputValues);

            individuals[ii].init(initialConnectionSetter);


        }

        for (int ii = 1; ii < params.getIndividuals(); ii++) {
            individuals[ii] = (Individual<T>) individuals[0].clone();

        }
    }
}
