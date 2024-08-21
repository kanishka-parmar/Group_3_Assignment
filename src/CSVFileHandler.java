import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CSVFileHandler implements MyFileHandler {

    private final CSVReader csvReader;
    private final CSVWriter csvWriter;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private List<Employee> employees;
    private int readIndex = 0;

    public CSVFileHandler(String filePath, boolean isWriting) throws IOException {
        File file = new File(filePath);

        if (isWriting) {
            this.csvWriter = new CSVWriter(new FileWriter(file));
            this.csvReader = null;
            employees = new ArrayList<>();
        } else {
            this.csvReader = new CSVReader(new FileReader(file));
            this.csvWriter = null;
            employees = new ArrayList<>();
            readExistingEmployees();
        }
    }

    private void readExistingEmployees() throws IOException {
        String[] line;
        try {
            while ((line = csvReader.readNext()) != null) {
                if (line.length >= 4) {
                    try {
                        String firstName = line[0];
                        String lastName = line[1];
                        String dobStr = line[2];
                        String experienceStr = line[3];

                        Employee employee = new Employee();
                        employee.setFirstName(firstName);
                        employee.setLastName(lastName);
                        employee.setDateOfBirth(dateFormat.parse(dobStr));
                        employee.setExperience(Integer.parseInt(experienceStr));

                        employees.add(employee);
                    } catch (ParseException | NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            if (csvReader != null) {
                csvReader.close();
            }
        }
    }

    @Override
    public Employee read() {
        if (readIndex < employees.size()) {
            return employees.get(readIndex++);
        }
        return null;
    }

    @Override
    public void write(Employee employee) {
        if (csvWriter != null) {
            String[] line = {
                    employee.getFirstName(),
                    employee.getLastName(),
                    dateFormat.format(employee.getDateOfBirth()),
                    String.valueOf(employee.getExperience())
            };
            csvWriter.writeNext(line);
        } else if (employees != null) {
            employees.add(employee);
        }
    }

    public void close() {
        if (csvWriter != null) {
            try {
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
