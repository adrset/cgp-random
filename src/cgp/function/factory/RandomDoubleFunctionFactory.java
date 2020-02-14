package cgp.function.factory;

import cgp.function.method.impl.Add;
import cgp.function.method.impl.Divide;
import cgp.function.method.impl.Multiply;
import cgp.function.method.impl.Subtract;

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
