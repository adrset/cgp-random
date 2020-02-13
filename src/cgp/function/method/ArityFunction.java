package cgp.function.method;

import cgp.simulation.ICloneable;

public interface ArityFunction<T> extends ICloneable {
    public T calculate(T[] args);
}

