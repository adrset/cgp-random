package cgp.function.method;

public class Add <T extends Number> implements ArityFunction <T>{


    @Override
    public <T1 extends Number> double calculate(T1... args) {
        return args[0].doubleValue() + args[1].doubleValue();
    }
}
