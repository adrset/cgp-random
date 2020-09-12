package cgp.user.function.method;

import cgp.lib.function.method.ArityFunction;

import java.util.List;

public class Multiply implements ArityFunction<Double> {

    @Override
    public Double calculate(List<Double> args) {
        return args.get(0) * args.get(1);
    }

    @Override
    public Multiply clone() {
        return new Multiply();
    }

}
