package ml;

import linalg.Matrix;

import java.util.ArrayList;

public class MatrixLoader {

    private ArrayList<String> lines;

    public MatrixLoader() {
        lines = new ArrayList<>();
    }

    public void append(String line) {
        lines.add(line);
    }

    public Matrix load() {
        String line = lines.remove(0);
        String[] p = line.split(" ");
        int height = Integer.parseInt(p[0]);
        int width = Integer.parseInt(p[1]);
        float[] data = new float[p.length - 2];
        for(int i = 0; i < data.length; i++)
            data[i] = Float.parseFloat(p[i + 2]);
        return new Matrix(height, width, data);
    }

}
