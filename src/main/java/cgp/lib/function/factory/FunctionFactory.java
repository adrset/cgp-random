package cgp.lib.function.factory;

import cgp.lib.function.method.ArityFunction;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public abstract class FunctionFactory<T> {
    protected Map<Class<?>, ElementFactory> elementBuilder = new HashMap<>();

//    public <T> T buildElement(Type targetType)
//            throws Exception {
//        if (!elementBuilder.containsKey(targetType)) {
//            throw new IllegalArgumentException("Missing Element Factory for Type " + targetType);
//        }
//        return (T) elementBuilder.get(targetType).build();
//    }
    public abstract ArityFunction<T> getFunction() throws Exception;
}
