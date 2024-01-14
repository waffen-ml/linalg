package ml.modules;

import linalg.Matrix;
import ml.Stack;

public class Sequential extends OneVar {

    private OneVar[] modules;

    public Sequential(OneVar... modules) {
        this.modules = modules;
    }

    @Override
    public void setStack(Stack stack) {
        for(OneVar m : modules)
            m.setStack(stack);
    }

    @Override
    public Matrix forward(Matrix input) {
        for(OneVar m: modules)
            input = m.forward(input);
        return input;
    }

    @Override
    public Matrix backward(Matrix deriv) {
        for(int i = modules.length - 1; i >= 0; i--)
            deriv = modules[i].backward(deriv);
        return deriv;
    }
}
