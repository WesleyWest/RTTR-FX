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


    public abstract int getLastSequenceNumber(String tableName);
    public abstract void createTable(String tableName);

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
        ObservableList<Division> tmp = this.readDivisionsFromDB();
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

    public int findLastSequenceNumber(String query) {
        Statement statement = null;
        int result = 0;
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

    public <T extends SimpleObject> ObservableList<T> readSimpleObjectsListFromDB(String tableName,String objectName) {
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
                boolean isDeleted = rs.getBoolean(3);
                tmp.add((T) new SimpleObject(id, description, isDeleted));
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

    private class tmpRoles {
        String roleCode;
        String roleName;

        public tmpRoles(String roleCode, String roleName) {
            this.roleCode = roleCode;
            this.roleName = roleName;
        }
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
                    "SELECT * " +
                            "FROM users " +
                            "ORDER BY user_id";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("user_name");
                String password = rs.getString("user_password");
                String roleCode = rs.getString("user_role");
                boolean status = rs.getBoolean("user_status");
                boolean undeletable = rs.getBoolean("user_undeletable");
                int employee_id = rs.getInt("user_employee_id");
                boolean deleted = rs.getBoolean("user_isdeleted");

                Employee employee = getObjectByID(getEmployees(), employee_id);
                tmp.add(new User(id, name, password, Role.valueOf(roleCode.toUpperCase()), status, undeletable, employee, deleted));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            GUIData.showAlert("SQL exception [USERS]: " + e.getLocalizedMessage());
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
                            "ORDER BY division_code";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("division_id");
                String code = rs.getString("division_code");
                String description = rs.getString("division_description");
                boolean isDeleted = rs.getBoolean("division_isdeleted");
                tmp.add(new Division(id, code, description, isDeleted));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            GUIData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }
        AppData.printInLog("Employee divisions was read...");
//        System.out.println(tmp);
        ObservableList<Division> divisions = FXCollections.observableArrayList(tmp);
        return divisions;
    }

    public ObservableList<Position> readPositionsFromDB() {
        ArrayList<Position> tmp = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query =
                    "SELECT * " +
                            "FROM employee_positions " +
                            "ORDER BY position_weight DESC";

            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("position_id");
                String description = rs.getString("position_description");
                int weight = rs.getInt("position_weight");
                boolean isDeleted = rs.getBoolean("position_isdeleted");
                tmp.add(new Position(id, description, weight, isDeleted));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            GUIData.showAlert("SQL exception: " + e.getLocalizedMessage());
            opened = false;
        }
        AppData.printInLog("Employee positions was read...");
//        System.out.println(tmp);
        ObservableList<Position> positions = FXCollections.observableArrayList(tmp);
        return positions;
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
                boolean isDeleted = rs.getBoolean("employee_isdeleted");
                Division div = AppData.getObjectByID(AppData.getDivisions(), divisionID);
                Position pos = AppData.getObjectByID(AppData.getPositions(), positionID);
                tmp.add(new Employee(id, lastName, name, middleName, pos, div, isDeleted));
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
                String details = rs.getString("technic_description");
                int statusID = rs.getInt("technic_status_id");
                int typeID = rs.getInt("technic_type_id");
                int e_owner = rs.getInt("technic_owner_id");
                boolean isDeleted = rs.getBoolean("technic_isdeleted");

                SimpleObject<TechnicStatus> status = AppData.getObjectByID(AppData.getStatuses(), statusID);
                SimpleObject<TechnicType> type = AppData.getObjectByID(AppData.getTypes(), typeID);

                Employee owner = AppData.getObjectByID(AppData.getEmployees(), e_owner);

                tmp.add(new Technic(id, name, details, status, type, owner,isDeleted));
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
                int repairerID = rs.getInt("request_repairer_id");
                String changeHistory = rs.getString("request_change_history");

                Technic technic = AppData.getObjectByID(AppData.getTechnic(), tecnicID);
                User author = AppData.getObjectByID(AppData.getUsers(), authorID);
                User closer = AppData.getObjectByID(AppData.getUsers(), closerID);
                User repairer = AppData.getObjectByID(AppData.getUsers(), repairerID);

                Request req = new Request(id, technic, openTime, closeTime, problemDescription, decisionDescription, status, repairer, author, closer, changeHistory);
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

    public void handleUser(User user, boolean isNew) {
        int intStatus = (user.isActive()) ? 1 : 0;
        int intUndeletable = (user.isUndeletable()) ? 1 : 0;
        int intDeleted = (user.isDeleted()) ? 1 : 0;
        int employeeID = user.getEmployee().getID();
        String query = "";
        String report = "";
        if (isNew) {
            query =
                    "INSERT INTO users (user_name, user_password, user_role, user_status, user_undeletable, user_employee_id, user_isdeleted)" +
                            "VALUES ('" + user.getName() + "', '"
                            + user.getPassword() + "', '"
                            + user.getRoleAsObject().toString() + "', '"
                            + intStatus + "', '"
                            + intUndeletable + "', '"
                            + employeeID + "', '"
                            + intDeleted + "') ;";
            report = "added";
        } else {
            query =
                    "UPDATE users SET "
                            + "user_name = '" + user.getName() + "', "
                            + "user_password = '" + user.getPassword() + "', "
                            + "user_role = '" + user.getRoleAsObject().toString() + "', "
                            + "user_status = '" + intStatus + "', "
                            + "user_undeletable = '" + intUndeletable + "', "
                            + "user_employee_id = '" + employeeID + "', "
                            + "user_isdeleted = '" + intDeleted + "' "
                            + "WHERE user_id = " + user.getID() + ";";
            report = "edited";
        }
        AppData.printInLog((executeUpdateDB(query)) ? "Record " + report + "..." : "Something wrong...");
    }

    public void handleDivision(Division division, boolean isNew) {
        String query = "";
        String report = "";

        if (isNew) {
            query = "INSERT INTO employee_divisions (division_code, division_description, division_isdeleted)" +
                    "VALUES ('" + division.getCode() + "', '"
                    + division.getDescription() + "', '"
                    + "0') ;";
            report = "added";
        } else {
            query = "UPDATE employee_divisions SET "
                    + "division_code = '" + division.getCode() + "', "
                    + "division_description = '" + division.getDescription() + "', "
                    + "division_isdeleted = '0' "
                    + "WHERE division_id = " + division.getID() + ";";
            report = "edited";
        }
        AppData.printInLog((executeUpdateDB(query)) ? "Record " + report + "..." : "Something wrong...");
    }

    public void handlePositions(ObservableList<Position> positions) {
        String query = "";
        dropTable("employee_positions");
        createTable("employee_positions");
        query = "INSERT INTO 'employee_positions' VALUES \n";

        for(int i=0; i<positions.size(); i++) {
            int intDeleted = (positions.get(i).isDeleted())?1:0;
            char c = (i<positions.size()-1)?',':';';
            query+="("
                    +positions.get(i).getID()+
                    ", '"+positions.get(i).getDescription()+"', "
                    +(positions.size()-i)+", "
                    +intDeleted
                    +")"+c+"\n";
        }
        AppData.printInLog((executeUpdateDB(query)) ? "The table was re-created..." : "Something wrong...");
    }

    public void handleEmployee(Employee employee, boolean isNew) {
        int intDeleted = (employee.isDeleted()) ? 1 : 0;
        String query = "";
        String report = "";
        if (isNew) {
            query =
                    "INSERT INTO employees (employee_last_name, employee_name, employee_middle_name, employee_position_id," +
                            " employee_division_id, employee_isdeleted)" +
                            "VALUES ('" + employee.getLastName() + "', '"
                            + employee.getName() + "', '"
                            + employee.getMiddleName()+ "', '"
                            + employee.getPosition().getID() + "', '"
                            + employee.getDivision().getID() + "', '"
                            + intDeleted + "') ;";
            report = "added";
        } else {
            query =
                    "UPDATE employees SET "
                            + "employee_last_name = '" + employee.getLastName() + "', "
                            + "employee_name = '" + employee.getName() + "', "
                            + "employee_middle_name = '" + employee.getMiddleName() + "', "
                            + "employee_position_id = '" + employee.getPosition().getID() + "', "
                            + "employee_division_id = '" + employee.getDivision().getID() + "', "
                            + "employee_isdeleted = '" + intDeleted + "' "
                            + "WHERE employee_id = " + employee.getID() + ";";
            report = "edited";
        }
        AppData.printInLog((executeUpdateDB(query)) ? "Record " + report + "..." : "Something wrong...");
    }

    public void handleTechnic(Technic technic, boolean isNew) {
        int intDeleted = (technic.isDeleted()) ? 1 : 0;
        String query = "";
        String report = "";
        if (isNew) {
            query =
                    "INSERT INTO technic (technic_name, technic_description, technic_status_id," +
                            " technic_type_id, technic_owner_id, technic_isdeleted)" +
                            "VALUES ('" + technic.getName() + "', '"
                            + technic.getDescription() + "', '"
                            + technic.getStatus().getID()+ "', '"
                            + technic.getType().getID() + "', '"
                            + technic.getOwner().getID() + "', '"
                            + intDeleted + "') ;";
            report = "added";
        } else {
            query =
                    "UPDATE technic SET "
                            + "technic_name = '" + technic.getName() + "', "
                            + "technic_description = '" + technic.getDescription() + "', "
                            + "technic_status_id = '" + technic.getStatus().getID() + "', "
                            + "technic_type_id = '" + technic.getType().getID() + "', "
                            + "technic_owner_id = '" + technic.getOwner().getID() + "', "
                            + "technic_isdeleted = '" + intDeleted + "' "
                            + "WHERE technic_id = " + technic.getID() + ";";
            report = "edited";
        }
        AppData.printInLog((executeUpdateDB(query)) ? "Record " + report + "..." : "Something wrong...");
    }

    public void addEditOpenedRequest(Request request, boolean isNew) {
        int intStatus = (request.getStatus()) ? 1 : 0;
        String query = "";
        String report = "";
        if (isNew) {
            query =
                    "INSERT INTO requests (request_status, request_technic_id, request_open_date," +
                            " request_problem_description, request_author_id, request_repairer_id, request_change_history)" +
                            "VALUES ('" + 0 + "', '"
                            + request.getTechnicAsObject().getID() + "', '"
                            + request.getOpenDateTime()+ "', '"
                            + request.getProblemDescription() + "', '"
                            + request.getAuthor().getID()+ "', '"
                            + request.getRepairer().getID()+ "', '"
                            + request.getChangeHistory()+ "') ;";
            report = "added";
        } else {
            query =
                    "UPDATE requests SET "
                            + "request_status = '" + intStatus + "', "
                            + "request_technic_id = '" + request.getTechnicAsObject().getID() + "', "
                            + "request_open_date = '" + request.getOpenDateTime() + "', "
//                            + "request_close_date = '" + request.getCloseDateTime() + "', "
                            + "request_problem_description = '" + request.getProblemDescription() + "', "
                            + "request_author_id = '" + request.getAuthor().getID() + "', "
                            + "request_repairer_id = '" + request.getRepairer().getID() + "', "
//                            + "request_decision_description = '" + request.getDecisionDescription() + "', "
//                            + "request_closer_id = '" + request.getCloser().getID() + "', "
                            + "request_change_history = '" + request.getChangeHistory() + "' "
                            + "WHERE request_id = " + request.getID() + ";";
            report = "edited";
        }
        AppData.printInLog((executeUpdateDB(query)) ? "Record " + report + "..." : "Something wrong...");
    }

    public void closeOpenedRequest(Request request, boolean isNew) {

    }

    public void handleSimpleObject(SimpleObject object, boolean isNew, String type) {
        int intDeleted = (object.isDeleted()) ? 1 : 0;
        String tableName = (type.equals("type")) ? "technic_types" : "technic_statuses";
        String fieldPrefix = (type.equals("type")) ? "technic_type" : "technic_status";
        String query = "";
        String report = "";
        if (isNew) {
            query =
                    "INSERT INTO "+tableName+" ("+fieldPrefix+"_description, "
                                                 +fieldPrefix+"_isdeleted)" +
                            "VALUES ('" + object.getDescription() + "', '"
                                        + intDeleted + "') ;";
            report = "added";
        } else {
            query =
                    "UPDATE "+tableName+" SET "
                            + fieldPrefix+"_description = '" + object.getDescription() + "', "
                            + fieldPrefix+"_isdeleted = '" + intDeleted + "' "
                            + "WHERE "+fieldPrefix+"_id = " + object.getID() + ";";
            report = "edited";
        }
        AppData.printInLog((executeUpdateDB(query)) ? "Record " + report + "..." : "Something wrong...");
    }


    public void markRecordAsDeleted(String tableName, String isDeletedFieldName, String idFieldName, int id) {
        String query = "UPDATE " + tableName + " SET " + isDeletedFieldName + " = 1 WHERE " + idFieldName + " = " + id + ";";
        AppData.printInLog((executeUpdateDB(query)) ? "Record was marked as deleted..." : "Something wrong...");
    }



    public void dropTable(String tableName){
        String query="DROP TABLE "+tableName;
        executeUpdateDB(query);
    }

    protected boolean executeUpdateDB(String query) {
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            return true;
        } catch (SQLException e) {
            GUIData.showAlert(e.getLocalizedMessage()+" "+query);
            return false;
        }
    }

}