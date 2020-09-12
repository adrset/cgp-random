package cgp.user.function.method;

import cgp.lib.function.method.ArityFunction;

import java.util.List;

public class Sqrt implements ArityFunction<Double> {

    @Override
    public Divide clone() {
        return new Divide();
    }

    @Override
    public Double calculate(List<Double> args) {
        return Math.sqrt(args.get(0));
    }
}
