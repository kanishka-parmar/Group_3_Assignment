import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            DataGenerator dataGenerator = new DataGenerator();
            dataGenerator.generateFiles();

            MyController myController = new MyController();
            myController.processFiles();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

