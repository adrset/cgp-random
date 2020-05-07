package cgp.lib.function.method;

import cgp.lib.simulation.ICloneable;

import java.util.List;

public interface ArityFunction<T> extends ICloneable {
    public T calculate(List<T> args);
}

