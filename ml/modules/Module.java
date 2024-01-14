package ml.modules;

import ml.MatrixDumper;
import ml.MatrixLoader;
import ml.Stack;
import ml.optimizers.Optimizer;

public abstract class Module {

    protected Stack stack;
    protected Optimizer optim;

    public void dump(MatrixDumper dumper) {

    }

    public void load(MatrixLoader loader) {

    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public void setOptmizer(Optimizer optim) {
        this.optim = optim;
    }

}
