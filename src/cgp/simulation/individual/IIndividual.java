package cgp.simulation.individual;

import cgp.simulation.ICloneable;
import cgp.simulation.mutator.IMutator;
import cgp.simulation.node.INode;

public interface IIndividual extends ICloneable {
    void init();

    double evaluate();

    void setInputs(INode[] nodes);

    void setOutputs(INode[] nodes);

    void mutate(IMutator mutator);
}
