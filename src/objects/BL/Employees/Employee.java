package objects.BL.Employees;

import objects.BL.StandardBehavior;
import objects.BL.SimpleObject;

import java.util.ArrayList;

public class Employee implements StandardBehavior {
    private Integer id;
    private String lastName;
    private String name;
    private String middleName;
    private SimpleObject position;
    private Division division;


    public Employee(Integer id, String lastName, String name, String middleName, SimpleObject<Position> position, Division division) {
        this.id = id;
        this.lastName = lastName;
        this.name = name;
        this.middleName = middleName;
        this.position = position;
        this.division = division;

    }

    public static ArrayList<Employee> sortEmployeeList(ArrayList<Employee> tmpEmployees) {
        tmpEmployees.sort((Employee employee1, Employee employee2) -> {
            int tmp1 =
                    employee1.getDivision().getDescription().charAt(0) * -100
                            + employee1.getPosition().getID() * 10
                            + employee1.getLastName().charAt(0);
            int tmp2 =
                    employee2.getDivision().getDescription().charAt(0) * -100
                            + employee2.getPosition().getID() * 10
                            + employee2.getLastName().charAt(0);
            return tmp2 - tmp1;
        });
        return tmpEmployees;
    }

    public static String getFullEmployeeDescription(Employee employee) {
        return employee.position.getDescription() + " " + employee.division.getDescription() +
                " " + employee.lastName + " " + employee.name + " " + employee.getMiddleName();
    }

    public String getShortDescription() {
        if (this.id == 0) {
            return "Сотрудник не привязан";
        } else {
            String tmp;
            if (this.middleName!=null){
                tmp= this.middleName.charAt(0)+".";
            } else {
                tmp = "";
            }
            tmp = this.lastName + " "
                + this.name.charAt(0) + ". "+tmp+", "
                + this.position.getDescription() + ", "
                + this.division.getCode();
            return tmp;
        }
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

    public int compareTo(Employee employee){
        return this.position.getID()-employee.position.getID();
    }

    @Override
    public boolean isDeleted() {
        return false;
    }

    @Override
    public String toString() {
        return getShortDescription();

//                "Employee{" +
//                "id=" + id +
//                ", lastName='" + lastName + '\'' +
//                ", name='" + name + '\'' +
//                ", middleName='" + middleName + '\'' +
//                ", position=" + position +
//                ", division=" + division +
//                '}';
    }

}
