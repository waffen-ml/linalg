package ml.modules;

import linalg.Linalg;
import linalg.Matrix;
import ml.*;

public class Linear extends OneVar {

    private Matrix weights, bias;

    public Linear(int inputSize, int outputSize) {
        weights = Linalg.rand(-1f, 1f, inputSize, outputSize);
        bias = Linalg.rand(-1f, 1f, 1, outputSize);
    }

    @Override
    public void load(MatrixLoader loader) {
        weights = loader.load();
        bias = loader.load();
    }

    @Override
    public void dump(MatrixDumper dumper) {
        dumper.dump(weights);
        dumper.dump(bias);
    }

    public Matrix forward(Matrix input) {
        stack.save(input.detach());
        return input.dot(weights).applyToRows((r) -> r.add(bias));
    }

    public Matrix backward(Matrix outder) {
        Matrix x = stack.get();

        optim.apply(weights, x.transpose().dot(outder));
        optim.apply(bias, outder.sumAlongColumns());

        return outder.dot(weights.transpose());
    }
}
