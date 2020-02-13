package cgp.function.method.impl;

import cgp.function.method.ArityFunction;

public class Multiply implements ArityFunction<Double> {

    @Override
    public Double calculate(Double[] args) {
        return args[0] * args[1];
    }

    @Override
    public Multiply clone() {
        return new Multiply();
    }

}
