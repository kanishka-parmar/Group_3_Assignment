import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLFileHandler implements MyFileHandler {

    private final XmlMapper xmlMapper;
    private final File file;
    private List<Employee> employees;
    private int readIndex = 0;

    public XMLFileHandler(String filePath, boolean isWriting) throws IOException {
        this.xmlMapper = new XmlMapper();
        this.file = new File(filePath);

        if (isWriting) {
            employees = new ArrayList<>();
        } else {
            if (file.exists()) {
                employees = xmlMapper.readValue(file, xmlMapper.getTypeFactory().constructCollectionType(ArrayList.class, Employee.class));
            } else {
                throw new IOException("File not found: " + filePath);
            }
        }
    }

    @Override
    public Employee read() {
        if (employees != null && readIndex < employees.size()) {
            return employees.get(readIndex++);
        }
        return null;
    }

    @Override
    public void write(Employee employee) {
        if (employees != null) {
            employees.add(employee);
        }
    }

    public void close() {
        if (employees != null && !employees.isEmpty()) {
            try {
                xmlMapper.writeValue(file, employees);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
