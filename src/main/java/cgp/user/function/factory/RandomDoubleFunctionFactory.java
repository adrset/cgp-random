package cgp.user.function.factory;

import cgp.lib.function.factory.RandomFunctionFactory;
import cgp.user.function.method.dbl.Add;
import cgp.user.function.method.dbl.Divide;
import cgp.user.function.method.dbl.Multiply;
import cgp.user.function.method.dbl.Subtract;

public class RandomDoubleFunctionFactory extends RandomFunctionFactory<Double> {
    {
        // Use reflections in order to automatically scan for classes that are ArityFunctions
        elementBuilder.put(Add.class, Add::new);
        elementBuilder.put(Divide.class, Divide::new);
        elementBuilder.put(Multiply.class, Multiply::new);
        elementBuilder.put(Subtract.class, Subtract::new);
    }
}
