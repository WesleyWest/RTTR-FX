package objects.BL.Users;

import objects.BL.idReturnable;

public class User implements idReturnable {
    private int ID;
    private String name;
    private String password;
    private Role role;
    private boolean status;
    private boolean undeletable;

    public User(int ID, String name, String password, Role role, boolean userStatus, boolean undeletable) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.role = role;
        this.status = userStatus;
        this.undeletable = undeletable;
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

    public void setStatus(boolean status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "User{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", undeletable=" + undeletable +
                '}';
    }

    public boolean getRealStatus() {
        return status;
    }
}
