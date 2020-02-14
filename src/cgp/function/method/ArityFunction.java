package cgp.function.method;

import cgp.simulation.ICloneable;

import java.util.List;

public interface ArityFunction<T> extends ICloneable {
    public T calculate(List<T> args);
}

