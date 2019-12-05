package objects.BL.Users;

import objects.BL.Employees.Employee;
import objects.BL.StandardBehavior;

public class User implements StandardBehavior {
    private int ID;
    private String name;
    private String password;
    private Role role;
    private boolean status;
    private boolean undeletable;
    private Employee employee;
    private boolean deleted;


    public User(int ID, String name, String password, Role role, boolean userStatus, boolean undeletable, Employee employee, boolean deleted) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.role = role;
        this.status = userStatus;
        this.undeletable = undeletable;
        this.deleted = deleted;
        this.employee = employee;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role.getRoleName();
    }

    public Role getRoleAsObject() {
        return role;
    }

    public String getStatus() {
        if (status) {
            return "Активен";
        } else {
            return "Неактивен";
        }
    }

    public boolean isActive() {
        return status;
    }

    public boolean isUndeletable() {
        return undeletable;
    }

    public Employee getEmployee() {
        return employee;
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", undeletable=" + undeletable +
                ", employee=" + employee +
                ", deleted=" + deleted +
                '}';
    }
}
