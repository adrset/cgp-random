package cgp.function.method.impl;

import cgp.function.method.ArityFunction;

import java.util.List;

public class Subtract implements ArityFunction<Double> {

    @Override
    public Double calculate(List<Double> args) {
        return args.get(0) - args.get(1);
    }

    @Override
    public Subtract clone() {
        return new Subtract();
    }
}
