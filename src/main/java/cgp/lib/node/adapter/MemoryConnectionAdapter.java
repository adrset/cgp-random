package cgp.lib.node.adapter;

import cgp.lib.node.Node;

import java.util.ArrayList;
import java.util.List;

public class MemoryConnectionAdapter<T> extends ConnectionAdapter<T> {
    List<Integer> inputLocations;
    public MemoryConnectionAdapter(int maxArity){
        super(maxArity);
        // Setting default location
        inputLocations = new ArrayList<>();

        for (int ii=0;ii<maxArity;ii++){
            inputLocations.add(0);
        }
    }

    public List<Integer> getInputLocations() {
        return inputLocations;
    }

    public void setInputLocations(List<Integer> inputLocations) {
        this.inputLocations = inputLocations;
    }

    @Override
        public MemoryConnectionAdapter<T> clone() {
            MemoryConnectionAdapter<T> ca = new MemoryConnectionAdapter<>(this.inputs.size());
            List<Node<T>> clone = new ArrayList<>(this.inputs);
            for (Node<T> node: super.inputs) {
                clone.add(node.clone());
            }
            ca.setInputs(clone);
            ca.inputLocations = new ArrayList<>(inputLocations);
            return ca;
    }
}
