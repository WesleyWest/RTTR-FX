package conf;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import objects.Employees.Division;
import objects.Employees.Employee;
import objects.Employees.Position;
import objects.MySQLDataBase;
import objects.Request;
import objects.Technic.TechnicType;
import objects.Technic.Technic;
import objects.Technic.TechnicStatus;
import objects.Users.Role;
import objects.Users.User;

public class AppData {
    private static User user = null;
    private static MySQLDataBase db = null;

    private static ObservableList<Role> roles = null;
    private static ObservableList<User> users = null;

    private static ObservableList<Position> positions;
    private static ObservableList<Division> divisions;
    private static ObservableList<Employee> employees;


    private static ObservableList<TechnicType> types;
    private static ObservableList<TechnicStatus> statuses;
    private static ObservableList<Technic> technic;

    private static ObservableList<Request> requests;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        AppData.user = user;
    }

    public static MySQLDataBase getDb() {
        return db;
    }

    public static void setDb(MySQLDataBase db) {
        AppData.db = db;
    }

    public static ObservableList<Role> getRoles() {
        return roles;
    }

    public static void setRoles(ObservableList<Role> roles) {
        AppData.roles = roles;
    }

    public static ObservableList<User> getUsers() {
        return users;
    }

    public static void setUsers(ObservableList<User> users) {
        AppData.users = users;
    }

    public static ObservableList<Position> getPositions() {
        return positions;
    }

    public static void setPositions(ObservableList<Position> positions) {
        AppData.positions = positions;
    }

    public static ObservableList<Division> getDivisions() {
        return divisions;
    }

    public static void setDivisions(ObservableList<Division> divisions) {
        AppData.divisions = divisions;
    }

    public static ObservableList<Employee> getEmployees() {
        return employees;
    }

    public static void setEmployees(ObservableList<Employee> employees) {
        AppData.employees = employees;
    }

    public static ObservableList<TechnicType> getTypes() {
        return types;
    }

    public static void setTypes(ObservableList<TechnicType> types) {
        AppData.types = types;
    }

    public static ObservableList<TechnicStatus> getStatuses() {
        return statuses;
    }

    public static void setStatuses(ObservableList<TechnicStatus> statuses) {
        AppData.statuses = statuses;
    }

    public static ObservableList<Technic> getTechnic() {
        return technic;
    }

    public static void setTechnic(ObservableList<Technic> technic) {
        AppData.technic = technic;
    }

    public static ObservableList<Request> getRequests() {
        return requests;
    }

    public static void setRequests(ObservableList<Request> requests) {
        AppData.requests = requests;
    }

    public static void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        stage.getIcons().add(new Image("resources/main.png"));

        alert.setTitle("RTTR-Master");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static <T extends ObjectInterface> T getObjectByID(ObservableList<T> list, int ID) {
        for (T t : list) {
            if (t.getID() == ID) return t;
        }
        return null;
    }
}
