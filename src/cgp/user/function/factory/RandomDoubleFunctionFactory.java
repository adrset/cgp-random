package cgp.user.function.factory;

import cgp.function.factory.RandomFunctionFactory;
import cgp.user.function.method.Add;
import cgp.user.function.method.Divide;
import cgp.user.function.method.Multiply;
import cgp.user.function.method.Subtract;

public class RandomDoubleFunctionFactory extends RandomFunctionFactory<Double> {
    {
        // Use reflections in order to automatically scan for classes that are ArityFunctions
        elementBuilder.put(Add.class, () -> {
            return new Add();
        });
        elementBuilder.put(Divide.class, () -> {
            return new Divide();
        });
        elementBuilder.put(Multiply.class, () -> {
            return new Multiply();
        });
        elementBuilder.put(Subtract.class, () -> {
            return new Subtract();
        });
    }
}
