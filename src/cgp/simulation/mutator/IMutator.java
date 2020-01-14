package cgp.simulation.mutator;

import cgp.simulation.node.INode;

public interface IMutator {
    public void mutateConnections (INode nodes[][]);
    public void mutateFunctions (INode nodes[][]);

}
