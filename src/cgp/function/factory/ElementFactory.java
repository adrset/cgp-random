package cgp.function.factory;

import cgp.function.method.ArityFunction;

public interface ElementFactory {
    ArityFunction build() throws Exception;
}