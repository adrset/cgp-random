package cgp.lib.function.factory;

import cgp.lib.function.method.ArityFunction;

public interface ElementFactory {
    ArityFunction build() throws Exception;
}