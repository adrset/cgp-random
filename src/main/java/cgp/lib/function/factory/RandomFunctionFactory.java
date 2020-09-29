package cgp.lib.function.factory;

import cgp.lib.function.method.ArityFunction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class RandomFunctionFactory<T> extends FunctionFactory<T> {
    Random generator;

    public RandomFunctionFactory(){
        this.generator = new Random(System.currentTimeMillis());
    }
    @Override
    public ArityFunction<T> getFunction() throws Exception{
        return randomFunction();
    }

    /*
    Just for quick testing! XD
     */
    private ArityFunction<T> randomFunction() throws Exception{

        List<Class<?>> keysAsArray = new ArrayList<>(elementBuilder.keySet());
        return elementBuilder.get(keysAsArray.get(generator.nextInt(keysAsArray.size()))).build();

    }
}
