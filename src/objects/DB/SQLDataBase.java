package objects.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import objects.BL.AppData;
import objects.BL.Employees.Division;
import objects.BL.Employees.Employee;
import objects.BL.Employees.Position;
import objects.BL.Request;
import objects.BL.SimpleObject;
import objects.BL.Technic.Technic;
import objects.BL.Technic.TechnicStatus;
import objects.BL.Technic.TechnicType;
import objects.BL.Users.Role;
import objects.BL.Users.User;
import objects.GUI.GUIData;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public abstract class SQLDataBase extends AppData {

    private class tmpRoles {
        String roleCode;
        String roleName;

        public tmpRoles(String roleCode, String roleName) {
            this.roleCode = roleCode;
            this.roleName = roleName;
        }
    }

    public abstract int getLastSequenceNumber(String tableName);

    private Connection connection;
    private boolean opened;

    private String connectionString;
    private String className;
    private String login;
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void open() {
        try {
            AppData.printInLog("Trying to connect to the DB...");
            Class.forName(className);
            opened = true;

        } catch (Exception e) {
            GUIData.showAlert("Class not found exception: " + e.getLocalizedMessage());
        } finally {
            AppData.printInLog("Connected successfully to: " + connectionString);
            AppData.printInLog("---------------------");
            AppData.printInLog("");
        }

    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean testConnection() {
        this.open();
        ObservableList<User> tmp = this.readUsersFromDB();
        this.close();
        return (tmp.size() > 0);
    }

    public void close() {
        try {
            getConnection().close();
            AppData.printInLog("Connection to database is closed...");
        } catch (Exception e) {
//            AppData.showAlert(e.getLocalizedMessage());
        }
    }

    public int findLastSequenceNumber(String query){
        Statement statement = null;
        int result=0;
        try {
            statement = getConnection().createStatement();
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                result = rs.getInt(1);
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            GUIData.showAlert(e.getLocalizedMessage());
        }
        return result;
    }

    public <T extends SimpleObject> ObservableList<T> readSimpleObjectsListFromDB(String tableName, String objectName) {
        ArrayList<T> tmp = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =
                    "SELECT * " +
                            "FROM " + tableName +
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
            GUIData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }
        AppData.printInLog(objectName + " was read...");
        ObservableList<T> objects = FXCollections.observableArrayList(tmp);
        return objects;
    }

    public void setRoleNamesFromDB() {
        ArrayList<tmpRoles> tmp = new ArrayList<>();
        ArrayList<Role> tmpRole = new ArrayList<>();
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
            GUIData.showAlert(e.getMessage());
            opened = false;
        }
        AppData.printInLog("Roles was set...");
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
            GUIData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }
        AppData.printInLog("Users was read...");
        AppData.printInLog("---------------------");
        AppData.printInLog("");
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
                tmp.add(new Division(id, code, description));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            GUIData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }
        AppData.printInLog("Employee divisions was read...");
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
            GUIData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }
        AppData.printInLog("Employees was read...");
        AppData.printInLog("---------------------");
        AppData.printInLog("");

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

                tmp.add(new Technic(id, name, details, status, type, owner, repairer));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            GUIData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }

        AppData.printInLog("Technic was read...");
        AppData.printInLog("---------------------");
        AppData.printInLog("");

        ObservableList<Technic> technic = FXCollections.observableArrayList(tmp);

        return technic;
    }


    public ObservableList<Request> readRequestsFromDB(boolean isClosed) {

        ArrayList<Request> tmp = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =
                    "SELECT * " +
                            " FROM requests " +
                            " WHERE request_status=" + ((isClosed) ? 1 : 0) +
                            " ORDER BY 1 ";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("request_id");
                boolean status = rs.getBoolean("request_status");
                int tecnicID = rs.getInt("request_technic_id");

                String timeStr = rs.getString("request_open_date");
                Timestamp openTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeStr).getTime());
                timeStr = rs.getString("request_close_date");

                Timestamp closeTime = null;

                if (status) {
                    closeTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(timeStr).getTime());
                }

                String problemDescription = rs.getString("request_problem_description");
                String decisionDescription = rs.getString("request_decision_description");
                int authorID = rs.getInt("request_author_id");
                int closerID = rs.getInt("request_closer_id");

                Technic technic = AppData.getObjectByID(AppData.getTechnic(), tecnicID);
                User author = AppData.getObjectByID(AppData.getUsers(), authorID);
                User closer = AppData.getObjectByID(AppData.getUsers(), closerID);

                Request req = new Request(id, technic, openTime, closeTime, problemDescription, decisionDescription, status, author, closer);
                tmp.add(req);
            }

            rs.close();
            statement.close();
        } catch (SQLException e) {
            GUIData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        } catch (ParseException e) {
            GUIData.showAlert("Parse exception: " + e.getLocalizedMessage());
            opened = false;
        }

        AppData.printInLog("Requests was read...");
        AppData.printInLog("---------------------");
        AppData.printInLog("");
        ObservableList<Request> requests = FXCollections.observableArrayList(tmp);
        return requests;
    }

    public void addNewUser(User user) {
        int intStatus = (user.isActive()) ? 1 : 0;
        int intUndeletable =(user.isUndeletable())?1:0;
        String query =
                "INSERT INTO users (user_name, user_password, user_role, user_status, user_undeletable)" +
                        "VALUES ('" + user.getName() + "', '"
                                    + user.getPassword() + "', '"
                                    + user.getRoleAsObject().toString() + "', '"
                                    + intStatus + "', '"
                                    + intUndeletable+"') ;";
        System.out.println((executeUpdateDB(query)) ? "Record added" : "Something wrong");
    }

    private boolean executeUpdateDB(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            return true;
        } catch (SQLException e) {
            GUIData.showAlert(e.getLocalizedMessage());
            return false;
        }
    }

}
