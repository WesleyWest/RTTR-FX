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
import objects.Technic.Technic;
import objects.Technic.TechnicStatus;
import objects.Technic.TechnicType;
import objects.Users.Role;
import objects.Users.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AppData {
    private static User user = null;
    private static MySQLDataBase db = null;

    private static ObservableList<Role> roles = null;
    private static ObservableList<User> users = null;

    private static ObservableList<SimpleObject<Position>> positions;
    private static ObservableList<Division> divisions;
    private static ObservableList<Employee> employees;


    private static ObservableList<SimpleObject<TechnicType>> types;
    private static ObservableList<SimpleObject<TechnicStatus>> statuses;
    private static ObservableList<Technic> technic;

    private static ObservableList<Request> requests;

    private static String pathCSS="";

    public static String getPathCSS() {
        return pathCSS;
    }

    public static void setPathCSS(String pathCSS) {
        AppData.pathCSS = pathCSS;
    }

    public static ObservableList<SimpleObject<Position>> getPositions() {
        return positions;
    }

    public static void setPositions(ObservableList<SimpleObject<Position>> positions) {
        AppData.positions = positions;
    }

    public static ObservableList<SimpleObject<TechnicType>> getTypes() {
        return types;
    }

    public static void setTypes(ObservableList<SimpleObject<TechnicType>> types) {
        AppData.types = types;
    }

    public static ObservableList<SimpleObject<TechnicStatus>> getStatuses() {
        return statuses;
    }

    public static void setStatuses(ObservableList<SimpleObject<TechnicStatus>> statuses) {
        AppData.statuses = statuses;
    }

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

    public static <T extends AbstractObject> T getObjectByID(ObservableList<T> list, int ID) {
        for (T element : list) {
            if (element.getID() == ID) {
                return element;
            }
        }
        return null;
    }

    public static void printInLog(String str) {
        if (str.length() != 0) {
            String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());

            System.out.println(timeStamp + ": " + str);
        } else {
            System.out.println();
        }
    }

}
