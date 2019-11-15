package objects.BL.Employees;

import objects.BL.idReturnable;
import objects.BL.SimpleObject;
import javafx.collections.ObservableList;
import objects.BL.Users.User;

public class Employee implements idReturnable {
    private Integer id;
    private String lastName;
    private String name;
    private String middleName;
    private SimpleObject position;
    private Division division;
    private User user;

    public Employee(Integer id, String lastName, String name, String middleName, SimpleObject<Position> position, Division division, User user) {
        this.id = id;
        this.lastName = lastName;
        this.name = name;
        this.middleName = middleName;
        this.position = position;
        this.division = division;
        this.user = user;
    }

    public static String getFullEmployeeDescription(Employee employee) {
        return employee.position.getDescription() + " " + employee.division.getDescription() +
                " " + employee.lastName + " " + employee.name + " " + employee.getMiddleName();
    }

    public static Employee getEmployeeByUserID (ObservableList<Employee> employees, int userID){
        for (Employee employee : employees) {
            if (employee.getUser().getID()==userID) return employee;
        }
        return null;
    }

    public Integer getID() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        if (middleName != null) {
            return middleName;
        } else {
            return "";
        }
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public SimpleObject getPosition() {
        return position;
    }

    public void setPosition(SimpleObject position) {
        this.position = position;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", name='" + name + '\'' +
                ", middleName='" + middleName + '\'' +
                ", position=" + position +
                ", division=" + division +
                ", user=" + user +
                '}';
    }
}
