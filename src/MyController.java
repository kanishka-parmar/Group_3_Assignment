
import java.io.IOException;

public class MyController {

    public void processFiles() {
        MyCollection collection = new MyCollection();

        Thread csvReaderThread = new Thread(() -> {
            try {
                CSVFileHandler csvHandler = new CSVFileHandler("/Users/kanishkaparmar/IdeaProjects/Group_3_Assignment/src/mock_data.csv", false);
                Employee employee;
                while ((employee = csvHandler.read()) != null) {
                    collection.add(employee);
                }
                csvHandler.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread xmlReaderThread = new Thread(() -> {
            try {
                XMLFileHandler xmlHandler = new XMLFileHandler("/Users/kanishkaparmar/IdeaProjects/Group_3_Assignment/src/dataset-2.xml", false);
                Employee employee;
                while ((employee = xmlHandler.read()) != null) {
                    collection.add(employee);
                }
                xmlHandler.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread jsonReaderThread = new Thread(() -> {
            try {
                JsonFileHandler jsonHandler = new JsonFileHandler("/Users/kanishkaparmar/IdeaProjects/Group_3_Assignment/src/MOCK_DATA.json", false);
                Employee employee;
                while ((employee = jsonHandler.read()) != null) {
                    collection.add(employee);
                }
                jsonHandler.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        csvReaderThread.start();
        xmlReaderThread.start();
        jsonReaderThread.start();

        try {
            csvReaderThread.join();
            xmlReaderThread.join();
            jsonReaderThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        System.out.println("Read Count: " + collection.getWriteCounter());

        Thread csvWriterThread = new Thread(() -> {
            try {
                CSVFileHandler csvHandler = new CSVFileHandler("output.csv", true);
                writeRecords(csvHandler, collection);
                System.out.println("Data written to output.csv");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread xmlWriterThread = new Thread(() -> {
            try {
                XMLFileHandler xmlHandler = new XMLFileHandler("output.xml", true);
                writeRecords(xmlHandler, collection);
                System.out.println("Data written to output.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        Thread jsonWriterThread = new Thread(() -> {
            try {
                JsonFileHandler jsonHandler = new JsonFileHandler("output.json", true);
                writeRecords(jsonHandler, collection);
                System.out.println("Data written to output.json");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        csvWriterThread.start();
        xmlWriterThread.start();
        jsonWriterThread.start();

        try {
            csvWriterThread.join();
            xmlWriterThread.join();
            jsonWriterThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    private static void writeRecords(MyFileHandler fileHandler, MyCollection collection) throws IOException {
        for (int i = 0; i < 100; i++) {
            Employee employee = collection.get();
            if (employee != null) {
                fileHandler.write(employee);
            }
        }
        fileHandler.close();
    }
}