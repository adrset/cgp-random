package cgp.simulation;

import cgp.function.factory.FunctionFactory;
import cgp.function.factory.RandomFunctionFactory;
import cgp.simulation.node.factory.NodeFactory;
import cgp.simulation.individual.IIndividual;
import cgp.simulation.individual.Individual;
import cgp.simulation.input.InputParams;
import cgp.simulation.mutator.IMutator;
import cgp.simulation.mutator.RandomMutator;

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

    public SimulationModel(InputParams params) {

        this.columns = params.getColumns();
        this.rows    = params.getRows();
        this.generator = new Random();
        this.params = params;
        factory = new RandomFunctionFactory();
        individuals = new Individual[params.getIndividuals()];
        mutator = new RandomMutator(params, factory);
        nodeFactory = new NodeFactory(params,factory,mutator);
    }

    @Override
    public void run() {
        int currentGeneration = 1;
        while (currentGeneration++ <= 100/*params.getGenerationThreshold() && evaluate() > params.getMinError()*/) {
            for (int ii = 0; ii < 4;ii++) {
                IIndividual i = individuals[0];
                try {
                    IIndividual j = (Individual)((Individual)i).clone();
                    //System.out.println(j + " " + i);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        for (int ii = 0; ii < this.individuals.length; ii++) {
            individuals[ii].evaluate();
        }
    }

    @Override
    public double evaluate() {
        return 1;
    }

    @Override
    public void init(){
        for (int ii=0;ii<params.getIndividuals();ii++){
            individuals[ii] = new Individual(this.columns, this.rows, params, nodeFactory);
            individuals[ii].init();
        }
    }
}
