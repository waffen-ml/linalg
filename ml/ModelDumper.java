package ml;
import java.io.*;

import ml.modules.Module;


public abstract class ModelDumper {

    public static void dumpModel(Module model, String path) throws IOException {
        MatrixDumper dumper = new MatrixDumper();
        model.dump(dumper);
        try (PrintWriter out = new PrintWriter(path)) {
            out.println(dumper);
        }
    }

    public static void loadModel(Module model, String path) throws IOException {
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line = br.readLine();
            MatrixLoader loader = new MatrixLoader();

            while (line != null) {
                loader.append(line);
                line = br.readLine();
            }

            model.load(loader);
        }
    }

}
