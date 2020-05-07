package cgp.lib.simulation;

import cgp.lib.function.factory.FunctionFactory;
import cgp.lib.individual.IIndividual;
import cgp.lib.node.factory.NodeFactory;
import cgp.lib.simulation.mutator.IMutator;
import cgp.lib.simulation.mutator.InitialRandomMutator;
import cgp.lib.individual.Individual;
import cgp.lib.input.InputParams;
import cgp.lib.simulation.mutator.RandomMutator;

import java.util.Random;

public class SimulationModel<T> implements ISimulation<T>{

    private int columns;
    private int rows;
    Random generator;
    IIndividual<T> individuals[];
    private InputParams params;
    private FunctionFactory factory;
    private NodeFactory nodeFactory;
    private IMutator mutator;
    private IMutator initialMutator;
    private T[] inputValues;

    public SimulationModel(InputParams params, FunctionFactory<T> factory, T defaultValue, T[] inputValues) {
        this.inputValues = inputValues;
        this.columns = params.getColumns();
        this.rows    = params.getRows();
        this.generator = new Random();
        this.params = params;
        this.factory = factory;
//        factory = new RandomFunctionFactory();
        individuals = new Individual[params.getIndividuals()];
        mutator = new RandomMutator<T>(params, factory);
        initialMutator = new InitialRandomMutator<T>(params, factory);
        nodeFactory = new NodeFactory<T>(params,factory,mutator, defaultValue);
    }

    @Override
    public void run() {
        try {
           // System.setOut(new PrintStream(new File("output-file.txt")));


            int currentGeneration = 0;
            while (currentGeneration++ <= 3/*params.getGenerationThreshold() && evaluate() > params.getMinError()*/) {
                for (int ii = 0; ii < 4;ii++) {
                    individuals[ii].compute();
                    System.out.println("-" + (ii+1) + "-");
                    individuals[ii].mutate(mutator);
                    //individuals[ii].describe();
                }
                System.out.println("==========" + (currentGeneration) + "=========");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public double evaluate() {
        return 1;
    }

    @Override
    public void init(){
        for (int ii=0;ii<1;ii++){
            individuals[ii] = new Individual<T>(this.columns*this.rows, params, nodeFactory, inputValues);

            individuals[ii].init(initialMutator);


        }

        for (int ii=1;ii<params.getIndividuals();ii++){
            individuals[ii] = (Individual<T>) individuals[0].clone();

        }
        System.out.println("1");
    }
}
