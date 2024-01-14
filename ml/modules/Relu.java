package ml.modules;

import linalg.Matrix;

public class Relu extends OneVar {

    public Matrix forward(Matrix input) {
        Matrix m = input.detach().map((w) -> w > 0? 1 : 0);
        stack.save(m);
        return input.mul(m);
    }

    public Matrix backward(Matrix deriv) {
        return deriv.mul(stack.get());
    }

}
