package objects.BL.Employees;

import javafx.collections.ObservableList;
import objects.BL.StandardBehavior;
import objects.BL.SimpleObject;

import java.util.ArrayList;

public class Employee implements StandardBehavior {
    private Integer id;
    private String lastName;
    private String name;
    private String middleName;
    private Position position;
    private Division division;
    private boolean isDeleted;


    public Employee(Integer id, String lastName, String name, String middleName, Position position, Division division, boolean isDeleted) {
        this.id = id;
        this.lastName = lastName;
        this.name = name;
        this.middleName = middleName;
        this.position = position;
        this.division = division;
        this.isDeleted = isDeleted;
    }

    public static ObservableList<Employee> sortEmployeeList(ObservableList<Employee> tmpEmployees) {
        tmpEmployees.sort((Employee employee1, Employee employee2) -> {
            int tmp1 =
                    employee1.getDivision().getCode().charAt(0) * -100
                            + employee1.getPosition().getID() * -10
                            + employee1.getLastName().charAt(0);
            int tmp2 =
                    employee2.getDivision().getCode().charAt(0) * -100
                            + employee2.getPosition().getID() * -10
                            + employee2.getLastName().charAt(0);
            return tmp2 - tmp1;
        });
        return tmpEmployees;
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

    public String getFullName(){
        String middleName =(this.middleName!=null)?this.middleName:"";
        return this.lastName + " "
                +this.name+ " "
                +middleName;
    }

    public String getStringPosition(){
        return this.position.getDescription();
    }

    public String getStringDivision(){
        return this.division.getCode();
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return getShortDescription();
    }

}
