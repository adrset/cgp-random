package cgp.function.method;

public interface ArityFunction<T extends Number> {
    public <T extends Number> double calculate(T ...args);
}

