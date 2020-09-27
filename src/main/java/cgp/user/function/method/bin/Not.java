package cgp.user.function.method.bin;

import cgp.lib.function.method.ArityFunction;

import java.util.List;

public class Not implements ArityFunction<Boolean> {

    @Override
    public Boolean calculate(List<Boolean> args) {
        return !args.get(0);
    }

    @Override
    public Not clone() {
        return new Not();
    }

    @Override
    public String describe() {
        return "~";
    }
}
