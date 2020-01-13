package cgp.function.factory;

import cgp.function.method.*;

import java.util.Random;

public class RandomFunctionFactory implements IFunctionFactory {
    Random generator;

    public RandomFunctionFactory(){
        this.generator = new Random();
    }
    @Override
    public Function getFunction() {

        return randomFunction();
    }

    /*
    Just for quick testing! XD
     */
    private Function randomFunction(){
        double nextDouble = generator.nextDouble();
        double numOfFunctions = 4.0;
        double dx = 1.0 / numOfFunctions;
        double x = dx;
        if (nextDouble < dx) {
            return new Add();
        }
        if (nextDouble < dx * 2) {
            return new Divide();
        }
        if (nextDouble < dx * 3) {
            return new Multiply();
        }

        return new Subtract();

    }
}
