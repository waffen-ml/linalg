package ml;
import java.util.StringJoiner;

import linalg.Matrix;

public class MatrixDumper {
    private static int N_DIGITS = 10;
    private StringJoiner joiner;

    public MatrixDumper() {
        joiner = new StringJoiner("\n");
    }

    public void dump(Matrix m) {
        String prefix = m.getHeight() + " " + m.getWidth() + " ";
        StringJoiner j = new StringJoiner(" ", prefix, "");
        String p = "%." + N_DIGITS + "f";
        m.map((w) -> {
            j.add(String.format(p, w));
            return w;
        });
        joiner.add(j.toString());
    }

    @Override
    public String toString() {
        return joiner.toString();
    }
}
