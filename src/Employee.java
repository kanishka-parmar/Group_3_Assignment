import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
public class Employee {
    private String firstName;
    private String lastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "MM/dd/yyyy")
    private Date dateOfBirth;
    private double experience;

    public Employee(String firstName, String lastName, Date dateOfBirth, double experience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.experience = experience;
    }
    public Employee() {

    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
    }

}
