import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataGenerator {
    public void generateFiles() throws IOException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        CSVFileHandler csvHandler = new CSVFileHandler("employees.csv", true);
        XMLFileHandler xmlHandler = new XMLFileHandler("employees.xml",true);
        JsonFileHandler jsonHandler = new JsonFileHandler("employees.json", true);

        for (int i = 1; i <= 100; i++) {
            Employee employee = new Employee("FirstName" + i, "LastName" + i, new Date(), i + 0.5);
            csvHandler.write(employee);
            xmlHandler.write(employee);
            jsonHandler.write(employee);
        }

        csvHandler.close();
        xmlHandler.close();
        jsonHandler.close();
    }
}

