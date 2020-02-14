package cgp.function.factory;

import cgp.function.method.*;

import java.lang.reflect.Type;
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

        double numOfFunctions = elementBuilder.keySet().size();
        double dx = 1.0 / numOfFunctions;
        double x = dx;
        List<Class<?>> keysAsArray = new ArrayList<>(elementBuilder.keySet());
        return elementBuilder.get(keysAsArray.get(generator.nextInt(keysAsArray.size()))).build();

    }
}
