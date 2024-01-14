import linalg.Matrix;
import ml.ModelDumper;
import ml.modules.Linear;
import ml.Stack;
import ml.optimizers.MomentumOptimizer;
import ml.optimizers.RMSOptimizer;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        Linear lin = new Linear(2, 3);
        Stack stack = new Stack();
        RMSOptimizer optimizer = new RMSOptimizer(0.015f, 0.99999f, 0.000000001f);
        //MomentumOptimizer optimizer = new MomentumOptimizer(0.005f, 0.9f);
        lin.setStack(stack);
        lin.setOptmizer(optimizer);

        Matrix input = new Matrix(new float[][] {
                {1, 2},
                {3, 4},
                {5, 6},
                {0, 1},
                {-1, 1},
                {-5, -10}
        });
        Matrix correct = new Matrix(new float[][] {
                {10, 6, 1},
                {24, 12, 5},
                {38, 18, 9},
                {3, 3, -1},
                {-2, 3, -2},
                {-44, -30, -17}
        });

        int epochs = 200;

        for (int k = 0; k < epochs; k++) {
            Matrix output = lin.forward(input);

            float mse = output.detach().map((i, j, w) -> (float) Math.pow(w - correct.get(i, j), 2)).sum() / correct.getSize();

            output.print();
            System.out.println(mse);

            Matrix dedy = (output.add(correct.detach().mul(-1))).mul(2);

            lin.backward(dedy);
        }

        ModelDumper.dumpModel(lin, "hey.txt");
    }
}