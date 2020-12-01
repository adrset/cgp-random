package cgp.user.function.method.dbl;

import cgp.lib.function.method.ArityFunction;

import java.util.List;

public class Divide implements ArityFunction<Double> {
    public static final Double ERROR = 0.00001d;

    @Override
    public Divide clone() {
        return new Divide();
    }

    @Override
    public Double calculate(List<Double> args) {
        Double lowerValue = args.get(1);
        if(Math.abs(lowerValue) < ERROR){
            return 1d;
        }
        return args.get(0) / args.get(1);
    }

    @Override
    public String describe() {
        return "/";
    }

}
