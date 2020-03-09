package cgp.individual;

import cgp.simulation.ICloneable;
import cgp.simulation.mutator.IMutator;
import cgp.node.Node;

import java.util.List;

public interface IIndividual<T> extends ICloneable {
    void init(IMutator mutator);

    double evaluate();

    void mutate(IMutator mutator);

    void describe();
}
