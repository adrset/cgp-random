package cgp.function.method;

public class Subtract <T extends Number> implements Function<T> {

    @Override
    public <T extends Number> double calculate(T arg1, T arg2) {
        return arg1.doubleValue() - arg2.doubleValue();
    }
}
