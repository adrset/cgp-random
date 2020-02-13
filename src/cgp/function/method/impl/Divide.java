package cgp.function.method.impl;

import cgp.function.method.ArityFunction;

public class Divide implements ArityFunction<Double> {

    @Override
    public Divide clone() {
        return new Divide();
    }

    @Override
    public Double calculate(Double[] args) {
        return null;
    }
}
