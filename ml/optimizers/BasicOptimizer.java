package ml.optimizers;

import linalg.Matrix;

public class BasicOptimizer extends Optimizer {

    private float learningRate;

    public BasicOptimizer(float learningRate) {
        this.learningRate = learningRate;
    }


    @Override
    public void apply(Matrix param, Matrix deriv) {
        param.add(deriv.detach().mul(-learningRate));
    }
}
