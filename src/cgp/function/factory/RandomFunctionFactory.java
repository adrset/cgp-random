package cgp.function.factory;

import cgp.function.method.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomFunctionFactory extends FunctionFactory {
    Random generator;

    public RandomFunctionFactory(){
        this.generator = new Random(System.currentTimeMillis());
    }
    @Override
    public ArityFunction getFunction() throws Exception{
        return randomFunction();
    }

    /*
    Just for quick testing! XD
     */
    private ArityFunction randomFunction() throws Exception{

        double numOfFunctions = 4.0;
        double dx = 1.0 / numOfFunctions;
        double x = dx;
        List<Class<?>> keysAsArray = new ArrayList<>(elementBuilder.keySet());
        return elementBuilder.get(keysAsArray.get(generator.nextInt(keysAsArray.size()))).build();




    }
}
