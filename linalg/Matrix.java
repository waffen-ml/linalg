package linalg;

import linalg.operations.MatrixIndexedMapOperation;
import linalg.operations.MatrixIndexedOperation;
import linalg.operations.MatrixMapOperation;
import linalg.operations.MatrixOperation;

import java.util.Arrays;
import java.util.StringJoiner;


public class Matrix {
    private final int width, height, start, gap;
    private final boolean inverted;
    private float[] source;

    public Matrix(float[][] mtx) {
        height = mtx.length;
        width = mtx[0].length;
        source = new float[height * width];
        start = 0;
        gap = 0;
        inverted = false;

        for (int k = 0; k < height; k++) {
            if (mtx[k].length != width)
                throw new RuntimeException("Wrong number of items in axis");
            System.arraycopy(mtx[k], 0, source, k * width, width);
        }
    }

    public Matrix(int height, int width) {
        this(height, width, new float[height * width]);
    }

    public Matrix(int height, int width, float[] source) {
        this.width = width;
        this.height = height;

        if (source.length != width * height)
            throw new RuntimeException("Wrong sized source");

        this.source = source;
        start = 0;
        gap = 0;
        inverted = false;
    }

    public Matrix(int height, int width, int start, int gap, boolean inverted, float[] source) {
        this.height = height;
        this.width = width;
        this.start = start;
        this.gap = gap;
        this.inverted = inverted;
        this.source = source;
    }

    public Matrix transpose() {
        return new Matrix(width, height, start, gap, !inverted, source);
    }

    public Matrix fill(float val) {
        Arrays.fill(source, val);
        return this;
    }

    public Matrix map(MatrixMapOperation oper) {
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                set(i, j, oper.run(get(i, j)));
        return this;
    }

    public Matrix map(MatrixIndexedMapOperation oper) {
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                set(i, j, oper.run(i, j, get(i, j)));
        return this;
    }

    public Matrix mul(float k) {
        return map((w) -> w * k);
    }

    public Matrix mul(Matrix m) {
        if (!isAlike(m))
            return null;
        return map((i, j, w) -> w * m.get(i, j));
    }

    public Matrix add(Matrix m) {
        if(!isAlike(m))
            return null;
        return map((i, j, w) -> w + m.get(i, j));
    }

    public float sum() {
        float s = 0;
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width; j++)
                s += get(i, j);
        return s;
    }

    public Matrix sumAlongRows() {
        Matrix m = new Matrix(getHeight(), 1);
        applyToRows((i, r) -> m.set(i, 0, r.sum()));
        return m;
    }

    public Matrix sumAlongColumns() {
        Matrix m = new Matrix(1, getWidth());
        applyToColumns((i, c) -> m.set(0, i, c.sum()));
        return m;
    }

    public Matrix add(float k) {
        return map((w) -> w + k);
    }

    public Matrix applyToColumns(MatrixIndexedOperation oper) {
        for (int i = 0; i < width; i++)
            oper.run(i, getColumn(i));
        return this;
    }

    public Matrix applyToColumns(MatrixOperation oper) {
        return applyToColumns((i, c) -> oper.run(c));
    }

    public Matrix applyToRows(MatrixIndexedOperation oper) {
        for(int i = 0; i < height; i++)
            oper.run(i, getRow(i));
        return this;
    }

    public Matrix applyToRows(MatrixOperation oper) {
        return applyToRows((i, r) -> oper.run(r));
    }

    public boolean isAlike(Matrix m) {
        return m.getWidth() == width && m.getHeight() == height;
    }

    private int reduceIndex(int i, int j) {
        if (inverted)
            return start + j * (height + gap) + i;
        else
            return start + i * (width + gap) + j;
    }

    public Matrix getSlice(int y1, int x1, int y2, int x2) {
        int newWidth = x2 - x1 + 1;
        int newHeight = y2 - y1 + 1;
        return new Matrix(newHeight, newWidth, reduceIndex(y1, x1), gap + (inverted? height - newHeight : width - newWidth), inverted, source);
    }

    public Matrix getColumn(int i) {
        return getSlice(0, i, height - 1, i);
    }

    public Matrix getRow(int i) {
        return getSlice(i, 0, i, width - 1);
    }

    public Matrix set(int i, int j, float v) {
        source[reduceIndex(i, j)] = v;
        return this;
    }

    public Matrix detach() {
        Matrix m = new Matrix(height, width);
        return m.map((i, j, w) -> get(i, j));
    }

    public Matrix dot(Matrix m) {
        if (width != m.getHeight())
            throw new RuntimeException("Unable to perform matmul.");
        Matrix r = new Matrix(height, m.getWidth());
        return r.map((i, j, w) -> {
            float s = 0;
            for (int k = 0; k < width; k++)
                s += get(i, k) * m.get(k, j);
            return s;
        });
    }

    public float get(int i, int j) {
        return source[reduceIndex(i, j)];
    }

    @Override
    public String toString() {
        return toString(2);
    }

    public String toString(int d) {
        String p = "%." + d + "f";
        StringJoiner joiner = new StringJoiner("\n ", "[", "]");

        for(int i = 0; i < height; i++) {
            StringJoiner row = new StringJoiner(" ", "[", "]");
            for(int j = 0; j < width; j++)
                row.add(String.format(p, get(i, j)));
            joiner.add(row.toString());
        }

        return joiner.toString();
    }

    public void print(int d) {
        System.out.println(toString(d));
    }

    public void print() {
        print(2);
    }

    public int getSize() {
        return width * height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isInverted() {
        return inverted;
    }

    public float[] getSource() {
        return source;
    }
}
