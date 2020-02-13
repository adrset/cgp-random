package cgp.node.adapter;

import cgp.simulation.ICloneable;
import cgp.node.Node;

import java.util.ArrayList;
import java.util.List;

public class ConnectionAdapter implements ICloneable {
    List<Node> inputs;
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

    public List<Node> getNodes(){
        return inputs;
    }

    public void setInputs(List<Node> inputs){
        this.inputs = inputs;
    }

    @Override
    public ConnectionAdapter clone() {
        ConnectionAdapter ca = new ConnectionAdapter(this.inputs.size());
        List<Node> clone = new ArrayList<>(this.inputs);
        for (Node node: this.inputs) {
            clone.add(node.clone());
        }
        ca.setInputs(clone);
        return ca;
    }
}
