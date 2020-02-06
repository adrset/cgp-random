package cgp.simulation.mutator;

import cgp.simulation.node.INode;

public interface IMutator {
    INode[] mutateConnections (INode nodes[]);
    INode[] mutateFunctions (INode nodes[]);

}
