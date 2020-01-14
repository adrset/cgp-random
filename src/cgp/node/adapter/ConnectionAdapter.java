package cgp.node.adapter;

import cgp.node.INode;
import cgp.node.Node;

import java.util.ArrayList;
import java.util.List;

public class ConnectionAdapter {
    List<INode> inputs;
    Node output;
    public ConnectionAdapter(int maxArity){
        inputs = new ArrayList<>();
        for (int ii=0;ii<maxArity;ii++){
            inputs.add(null);
        }
        System.out.println(inputs.size());
    }

    public List<INode> getNodes(){
        return inputs;
    }



}
