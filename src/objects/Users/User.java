package objects.Users;

public class User {
    private int userID;
    private String userName;
    private String userPassword;
    private Role userRole;
    private boolean userStatus;
    private boolean userUndeletable;

    public User(int userID, String userName, String userPassword, Role userRole, boolean userStatus, boolean userUndeletable) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.userUndeletable = userUndeletable;
    }

    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public Role getUserRole() {
        return userRole;
    }

    public boolean isActive() {
        return userStatus;
    }

    public boolean isUserUndeletable() {
        return userUndeletable;
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userRole=" + userRole +
                ", userStatus=" + userStatus +
                ", userUndeletable=" + userUndeletable +
                '}';
    }
}
