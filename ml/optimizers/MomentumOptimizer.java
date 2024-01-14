package ml.optimizers;

import linalg.Matrix;

import java.util.HashMap;

public class MomentumOptimizer extends Optimizer {

    private float learningRate, beta;
    private HashMap<Matrix, Matrix> paramTable;

    public MomentumOptimizer(float learningRate, float beta) {
        this.learningRate = learningRate;
        this.beta = beta;
        paramTable = new HashMap<>();
    }

    @Override
    public void apply(Matrix param, Matrix deriv) {
        Matrix m = paramTable.getOrDefault(param, null);
        if (m == null) {
            m = deriv.detach().mul(1 - beta);
            paramTable.put(param, m);
        } else {
            m.mul(beta).add(deriv.detach().mul(1 - beta));
        }
        param.add(m.detach().mul(-learningRate));
    }
}
