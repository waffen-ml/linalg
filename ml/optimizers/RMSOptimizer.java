package ml.optimizers;

import linalg.Matrix;

import java.util.HashMap;

public class RMSOptimizer extends Optimizer {

    private float learningRate, beta, epsilon;
    private HashMap<Matrix, Matrix> paramTable;

    public RMSOptimizer(float learningRate, float beta, float epsilon) {
        this.learningRate = learningRate;
        this.beta = beta;
        this.epsilon = epsilon;
        paramTable = new HashMap<>();
    }

    @Override
    public void apply(Matrix param, Matrix deriv) {
        Matrix m = paramTable.getOrDefault(param, null);
        Matrix d = deriv.detach().mul(deriv).mul(1 - beta);

        if (m == null) {
            m = d;
            paramTable.put(param, m);
        } else {
            m.mul(beta).add(d);
        }

        Matrix m1 = m.detach().map((i, j, w) -> -learningRate * deriv.get(i, j) / (float)Math.sqrt(w + epsilon));
        param.add(m1);
    }
}
