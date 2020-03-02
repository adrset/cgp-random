package cgp.individual;

import cgp.simulation.ICloneable;
import cgp.simulation.mutator.IMutator;
import cgp.node.Node;

public interface IIndividual<T> extends ICloneable {
    void init(IMutator mutator);

    double evaluate();

    void setInputs(Node<T>[] nodes);

    void setOutputs(Node<T>[] nodes);

    Node<T>[] getNodes();

//    void mutate(IMutator mutator);
}