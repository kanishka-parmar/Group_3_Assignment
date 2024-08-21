import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileHandler implements MyFileHandler {

    private final ObjectMapper objectMapper;
    private final File file;
    private List<Employee> employees;
    private int readIndex = 0;

    public JsonFileHandler(String filePath, boolean isWriting) throws IOException {
        this.objectMapper = new ObjectMapper();
        this.file = new File(filePath);

        if (isWriting) {
            employees = new ArrayList<>();
        } else {
            if (file.exists()) {
                CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Employee.class);
                employees = objectMapper.readValue(file, listType);
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
        if (employees!=null) {
            employees.add(employee);
        }
    }

    public void close() {
        if (employees != null && !employees.isEmpty()) {
            try {
                objectMapper.writeValue(file, employees);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}