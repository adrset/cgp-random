package cgp.function.factory;

import cgp.function.method.*;

import java.util.HashMap;
import java.util.Map;

public abstract class FunctionFactory<T> {
    protected Map<Class<?>, ElementFactory> elementBuilder = new HashMap<>();

//    public static <T> T buildElement(Type targetType)
//            throws Exception {
//        if (!elementBuilder.containsKey(targetType)) {
//            throw new IllegalArgumentException("Missing Element Factory for Type " + targetType);
//        }
//        return (T) elementBuilder.get(targetType).build();
//    }
    public abstract ArityFunction getFunction() throws Exception;
}
