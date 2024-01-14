package linalg;

import java.util.Random;

public abstract class Linalg {
    private final static Random random = new Random();

    public static Matrix zeros(int height, int width) {
        return (new Matrix(height, width)).fill(0);
    }

    public static Matrix ones(int height, int width) {
        return (new Matrix(height, width)).fill(1);
    }

    public static Matrix arange(int height, int width) {
        Matrix r = new Matrix(height, width);
        return r.map((i, j, k) -> i * width + j);
    }

    public static Matrix rand(float min, float max, int height, int width) {
        Matrix r = new Matrix(height, width);
        return r.map((w) -> random.nextFloat(min, max));
    }

}
