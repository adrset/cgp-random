package cgp.lib.node.adapter;

import cgp.lib.simulation.ICloneable;
import cgp.lib.node.Node;

import java.util.ArrayList;
import java.util.List;

public class ConnectionAdapter<T> implements ICloneable {
    List<Node<T>> inputs;
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

    public List<Node<T>> getNodes(){
        return inputs;
    }

    public void setInputs(List<Node<T>> inputs){
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
