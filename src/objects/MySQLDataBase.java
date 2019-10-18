package objects;

import conf.AppData;
import conf.Configs;
import conf.SimpleObject;
import forms.Login.LoginController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.Employees.Division;
import objects.Employees.Employee;
import objects.Employees.Position;
import objects.Technic.Technic;
import objects.Technic.TechnicStatus;
import objects.Technic.TechnicType;
import objects.Users.Role;
import objects.Users.User;

import java.sql.*;
import java.util.ArrayList;


public class MySQLDataBase extends Configs {



    private class tmpRoles {
        String roleCode;
        String roleName;

        public tmpRoles(String roleCode, String roleName) {
            this.roleCode = roleCode;
            this.roleName = roleName;
        }
    }

    private Connection connection;

    public boolean isOpen() {
        return opened;
    }

    private boolean opened;


    public <T extends SimpleObject> ObservableList<T> readSimpleObjectsListFromDB(String tableName, String objectName) {
        ArrayList<T> tmp = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =
                    "SELECT * " +
                            "FROM "+ tableName+
                            " ORDER BY 1";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt(1);
                String description = rs.getString(2);
                tmp.add((T) new SimpleObject(id, description));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            AppData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }
        System.out.println(objectName+" was read...");
        ObservableList<T> objects = FXCollections.observableArrayList(tmp);
        return objects;
    }

    public void setRoleNamesFromDB() {
        ArrayList<tmpRoles> tmp = new ArrayList<tmpRoles>();
        try {
            Statement statement = connection.createStatement();
            String query =

                    "SELECT role_code,role_name " +
                            "FROM roles " +
                            "ORDER BY role_code ";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String roleCode = rs.getString("role_code");
                String roleName = rs.getString("role_name");
                Role.setRoleNameByCode(roleCode, roleName);
            }
            rs.close();
            statement.close();

        } catch (SQLException e) {
            AppData.showAlert(e.getMessage());
            opened = false;
        }
        System.out.println("Roles was set...");

    }

    public ObservableList<User> readUsersFromDB() {

        ArrayList<User> tmp = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =
                    "SELECT user_id,user_name,user_password,user_role,user_status,user_undeletable " +
                            "FROM Users " +
                            "ORDER BY user_id ";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String roleCode = rs.getString("user_role");
                Boolean status = rs.getBoolean("user_status");
                Boolean undeletable = rs.getBoolean("user_undeletable");
                tmp.add(new User(id, name, password, Role.valueOf(roleCode.toUpperCase()), status, undeletable));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            AppData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }
        System.out.println("Users was read...");
        System.out.println("---------------------");
        System.out.println();
        ObservableList<User> users = FXCollections.observableArrayList(tmp);
        return users;
    }

    public ObservableList<Division> readDivisionsFromDB() {
        ArrayList<Division> tmp = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =
                    "SELECT * " +
                            "FROM employee_divisions " +
                            "ORDER BY division_id ";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("division_id");
                String code = rs.getString("division_code");
                String description = rs.getString("division_description");
//                System.out.println(id+": ["+code+"] "+description);
                tmp.add(new Division(id, code, description));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            AppData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }
        System.out.println("Employee divisions was read...");
        ObservableList<Division> divisions = FXCollections.observableArrayList(tmp);
        return divisions;
    }

    public ObservableList<Employee> readEmployeesFromDB() {
        ArrayList<Employee> tmp = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =
                    "SELECT * " +
                            "FROM employees " +
                            "ORDER BY employee_id ";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("employee_id");
                String lastName = rs.getString("employee_last_name");
                String name = rs.getString("employee_name");
                String middleName = rs.getString("employee_middle_name");
                int positionID = rs.getInt("employee_position_id");
                int divisionID = rs.getInt("employee_division_id");
                int userID = rs.getInt("employee_user_id");

                Division div = AppData.getObjectByID(AppData.getDivisions(), divisionID);
                SimpleObject<Position> pos = AppData.getObjectByID(AppData.getPositions(), positionID);
                User user = AppData.getObjectByID(AppData.getUsers(), userID);

                tmp.add(new Employee(id, lastName, name, middleName, pos, div, user));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            AppData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }
        System.out.println("Employees was read...");
        System.out.println("---------------------");
        System.out.println();

        ObservableList<Employee> employees = FXCollections.observableArrayList(tmp);

        return employees;
    }

    public ObservableList<Technic> readTechnicFromDB() {
        ArrayList<Technic> tmp = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =
                    "SELECT * " +
                            "FROM technic " +
                            "ORDER BY 1 ";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("technic_id");
                String name = rs.getString("technic_name");
                String details = rs.getString("technic_details");
                int statusID = rs.getInt("technic_status_id");
                int typeID = rs.getInt("technic_type_id");
                int e_owner = rs.getInt("technic_owner");
                int e_repairer = rs.getInt("technic_repairer");

                SimpleObject<TechnicStatus> status = AppData.getObjectByID(AppData.getStatuses(), statusID);
                SimpleObject<TechnicType> type = AppData.getObjectByID(AppData.getTypes(), typeID);

                Employee owner = AppData.getObjectByID(AppData.getEmployees(), e_owner);
                Employee repairer = AppData.getObjectByID(AppData.getEmployees(), e_repairer);

                tmp.add(new Technic(id,name,details,status,type,owner,repairer));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            AppData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }

        System.out.println("Technic was read...");
        System.out.println("---------------------");
        System.out.println();

        ObservableList<Technic> technic = FXCollections.observableArrayList(tmp);

        return technic;
    }

    public ObservableList<Request> readActiveRequestsFromDB() {


        return null;
    }

    public void open() {
        try {
            String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbSchema;
            System.out.println("Trying to connect to the DB...");

            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(connectionString, dbUser, dbPass);
            System.out.println("Connected successfully to: " + connectionString);
            System.out.println("---------------------");
            System.out.println();
            opened = true;
        } catch (ClassNotFoundException e) {
            AppData.showAlert("Class not found: " + e.getLocalizedMessage());
        } catch (SQLException e) {
            AppData.showAlert("SQL exception: " + e.getLocalizedMessage());
        }
    }

    public void close() {
        try {
            connection.close();
            System.out.println("Connection to database is closed...");
        } catch (SQLException e) {
            AppData.showAlert(e.getLocalizedMessage());
        }
    }
}
