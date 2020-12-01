package cgp.lib.node.guard;

public interface ComputedValueGuard<T> {
    T guard(T value);
}
