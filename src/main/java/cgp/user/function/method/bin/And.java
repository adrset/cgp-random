package cgp.user.function.method.bin;

import cgp.lib.function.method.ArityFunction;

import java.util.List;

public class And implements ArityFunction<Boolean> {

    @Override
    public Boolean calculate(List<Boolean> args) {
        return args.get(0) && args.get(1);
    }

    @Override
    public And clone() {
        return new And();
    }

    @Override
    public String describe() {
        return "&";
    }
}
