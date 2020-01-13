package cgp.node.adapter;

import cgp.node.Node;

import java.util.ArrayList;
import java.util.List;

public class ConnectionAdapter {
    List<Node> inputs;
    Node output;
    public ConnectionAdapter(int maxArity){
        inputs = new ArrayList<>(maxArity);
    }

    public void changeConnection(Node oldNode, Node newNode) {
        inputs.set(inputs.indexOf(oldNode), newNode);
    }


}
