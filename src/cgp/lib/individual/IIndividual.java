package cgp.lib.individual;

import cgp.lib.node.OutputNode;
import cgp.lib.simulation.ICloneable;
import cgp.lib.simulation.mutator.IMutator;

import java.util.List;

public interface IIndividual<T> extends ICloneable {
    void init(IMutator mutator);

    void compute();

    List<OutputNode<T>> getOutputNodes();

    void mutate(IMutator mutator);

    void describe();
}
