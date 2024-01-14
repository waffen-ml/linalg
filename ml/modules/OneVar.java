package ml.modules;

import linalg.Matrix;

public abstract class OneVar extends Module {
    public abstract Matrix forward(Matrix m);
    public abstract Matrix backward(Matrix m);
}
