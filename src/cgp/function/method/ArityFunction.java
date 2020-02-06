package cgp.function.method;

import cgp.simulation.ICloneable;

public interface ArityFunction<T extends Number> extends ICloneable {
    public <T extends Number> double calculate(T[] args);
}

