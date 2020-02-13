package cgp.simulation.individual;

import cgp.simulation.ICloneable;
import cgp.simulation.mutator.IMutator;
import cgp.simulation.node.Node;

public interface IIndividual extends ICloneable {
    void init(IMutator mutator);

    double evaluate();

    void setInputs(Node[] nodes);

    void setOutputs(Node[] nodes);

    void mutate(IMutator mutator);
}
