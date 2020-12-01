package cgp.user.node.guard;

import cgp.lib.node.guard.ComputedValueGuard;

public class DoubleGuard implements ComputedValueGuard<Double> {

    @Override
    public Double guard(Double value) {
        if (Double.isNaN(value)) {
            return 0.;
        }
        if (Double.isInfinite(value)) {
            if (value > 0) {
                return Double.MAX_VALUE;
            } else {
                return -Double.MAX_VALUE;
            }
        }

        return value;

    }
}
