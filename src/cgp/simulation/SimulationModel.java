package cgp.simulation;

import cgp.function.factory.FunctionFactory;
import cgp.function.factory.RandomFunctionFactory;
import cgp.simulation.mutator.InitialRandomMutator;
import cgp.node.factory.NodeFactory;
import cgp.individual.IIndividual;
import cgp.individual.Individual;
import cgp.input.InputParams;
import cgp.simulation.mutator.IMutator;
import cgp.simulation.mutator.RandomMutator;

import java.io.*;
import java.util.Random;

public class SimulationModel implements ISimulation{

    private int columns;
    private int rows;
    Random generator;
    IIndividual individuals[];
    private InputParams params;
    private FunctionFactory factory;
    private NodeFactory nodeFactory;
    private IMutator mutator;
    private IMutator initialMutator;


    public SimulationModel(InputParams params) {

        this.columns = params.getColumns();
        this.rows    = params.getRows();
        this.generator = new Random();
        this.params = params;
        factory = new RandomFunctionFactory();
        individuals = new Individual[params.getIndividuals()];
        mutator = new RandomMutator(params, factory);
        initialMutator = new InitialRandomMutator(params, factory);
        nodeFactory = new NodeFactory(params,factory,mutator);
    }

    @Override
    public void run() {
        try {
            System.setOut(new PrintStream(new File("output-file.txt")));


            int currentGeneration = 1;
            while (currentGeneration++ <= 2/*params.getGenerationThreshold() && evaluate() > params.getMinError()*/) {
                for (int ii = 0; ii < 4;ii++) {
                    individuals[ii].evaluate();
                    System.out.println("==========" + (ii+1) + "=========");
                    individuals[ii].mutate(mutator);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public double evaluate() {
        return 1;
    }

    @Override
    public void init(){
        for (int ii=0;ii<params.getIndividuals();ii++){
            individuals[ii] = new Individual(this.columns*this.rows, params, nodeFactory);
            individuals[ii].init(initialMutator);
        }
    }
}
