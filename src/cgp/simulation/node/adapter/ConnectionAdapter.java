package cgp.simulation.node.adapter;

import cgp.simulation.ICloneable;
import cgp.simulation.node.INode;
import cgp.simulation.node.Node;

import java.util.ArrayList;
import java.util.List;

public class ConnectionAdapter implements ICloneable {
    List<INode> inputs;
    Node output;
    int maxArity;
    public ConnectionAdapter(int maxArity){
        inputs = new ArrayList<>();
        this.maxArity = maxArity;
        for (int ii=0;ii<maxArity;ii++){
            inputs.add(null);
        }

    }

    public int getMaxArity(){
        return maxArity;
    }

    public List<INode> getNodes(){
        return inputs;
    }

    public void setInputs(List<INode> inputs){
        this.inputs = inputs;
    }

    @Override
    public ConnectionAdapter clone() {
        ConnectionAdapter ca = new ConnectionAdapter(this.inputs.size());
        List<INode> clone = new ArrayList<>(this.inputs);
        for (INode node: this.inputs) {
            clone.add((INode)node.clone());
        }
        ca.setInputs(clone);
        return ca;
    }
}
