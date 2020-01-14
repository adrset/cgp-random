package cgp.simulation.mutator;

import cgp.node.INode;

public interface IMutator {
    public void mutateConnections (INode nodes[][]);
    public void mutateFunctions (INode nodes[][]);

}
