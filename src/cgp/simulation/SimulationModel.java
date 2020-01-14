package cgp.simulation;

import cgp.function.factory.IFunctionFactory;
import cgp.node.Node;
import cgp.node.factory.NodeFactory;
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
    private IFunctionFactory factory;
    private NodeFactory nodeFactory;
    private IMutator mutator;

    public SimulationModel(InputParams params) {

        this.columns = params.getColumns();
        this.rows    = params.getRows();
        this.generator = new Random();
        this.params = params;
        individuals = new Individual[params.getIndividuals()];
        nodeFactory = new NodeFactory(params);
        mutator = new RandomMutator(params, factory);
    }

    @Override
    public void run() {
        int currentGeneration = 1;
//        while (currentGeneration <= params.getGenerationThreshold() && evaluate() > params.getMinError()) {
//
//        }
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
