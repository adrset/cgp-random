package cgp.user.function.factory;

import cgp.lib.function.factory.RandomFunctionFactory;
import cgp.user.function.method.bin.And;
import cgp.user.function.method.bin.Not;
import cgp.user.function.method.bin.Or;

public class RandomBooleanFunctionFactory extends RandomFunctionFactory<Boolean> {
    {
        // Use reflections in order to automatically scan for classes that are ArityFunctions
        elementBuilder.put(Or.class, Or::new);
        elementBuilder.put(And.class, And::new);
        elementBuilder.put(Not.class, Not::new);
    }
}
