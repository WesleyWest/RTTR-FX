package objects.BL;

import javafx.collections.ObservableList;
import objects.DB.SQLDataBase;
import objects.BL.Employees.Division;
import objects.BL.Employees.Employee;
import objects.BL.Employees.Position;
import objects.BL.Technic.Technic;
import objects.BL.Technic.TechnicStatus;
import objects.BL.Technic.TechnicType;
import objects.BL.Users.Role;
import objects.BL.Users.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AppData {
    private static User user = null;
    private static SQLDataBase db = null;

    private static ObservableList<Role> roles = null;
    private static ObservableList<User> users = null;

    private static ObservableList<SimpleObject<Position>> positions;
    private static ObservableList<Division> divisions;
    private static ObservableList<Employee> employees;

    private static ObservableList<SimpleObject<TechnicType>> types;
    private static ObservableList<SimpleObject<TechnicStatus>> statuses;
    private static ObservableList<Technic> technic;

    private static ObservableList<Request> requests;

    public static SQLDataBase getDb() {
        return db;
    }

    public static void setDb(SQLDataBase db) {
        AppData.db = db;
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

    public static <T extends idReturnable> T getObjectByID(ObservableList<T> list, int ID) {
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
