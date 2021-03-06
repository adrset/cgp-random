package cgp.user.function.method.dbl;

import cgp.lib.function.method.ArityFunction;

import java.util.List;

public class Divide implements ArityFunction<Double> {

    @Override
    public Divide clone() {
        return new Divide();
    }

    @Override
    public Double calculate(List<Double> args) {
        return args.get(0) / args.get(1);
    }

    @Override
    public String describe() {
        return "/";
    }

}
