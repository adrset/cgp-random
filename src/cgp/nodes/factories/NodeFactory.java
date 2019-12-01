package cgp.nodes.factories;

import cgp.functions.Function;
import cgp.nodes.Node;

public abstract class NodeFactory{
    public Node getNode(Function f){

        return new Node(f);
    }

}
