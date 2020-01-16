package cgp.function.method;

public class Divide <T extends Number> implements ArityFunction <T>{

    @Override
    public <T extends Number> double calculate(T ...args) {
        return args[0].doubleValue() / args[1].doubleValue();
    }


    @Override
    public Divide clone() {
        return new Divide();
    }
}
