package cgp.user.function.method.dbl;

import cgp.lib.function.method.ArityFunction;

import java.util.List;

public class Add implements ArityFunction<Double> {

    @Override
    public Double calculate(List<Double> args) {
        return args.get(0) + args.get(1);
    }

    @Override
    public Add clone() {
        return new Add();
    }

    @Override
    public String describe() {
        return "+";
    }
}
