package ml.optimizers;

import linalg.Matrix;

public abstract class Optimizer {

    public abstract void apply(Matrix param, Matrix deriv);

}
