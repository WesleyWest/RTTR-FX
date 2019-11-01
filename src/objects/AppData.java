package objects;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import objects.DB.SQLDataBase;
import objects.Employees.Division;
import objects.Employees.Employee;
import objects.Employees.Position;
import objects.Technic.Technic;
import objects.Technic.TechnicStatus;
import objects.Technic.TechnicType;
import objects.Users.Role;
import objects.Users.User;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private static Window owner;

    private static String filePath = "src/conf/settings.dat";
    private static String dbHost;
    private static String dbPort;
    private static String dbUser;
    private static String dbPass;
    private static String dbSchema;
    private static String pathCSS;
    private static String SQLDataBaseType;
    private static String pathToSQLiteDB;


    public static String getDbHost() {
        return dbHost;
    }

    public static void setDbHost(String dbHost) {
        AppData.dbHost = dbHost;
    }

    public static String getDbPort() {
        return dbPort;
    }

    public static void setDbPort(String dbPort) {
        AppData.dbPort = dbPort;
    }

    public static String getDbUser() {
        return dbUser;
    }

    public static void setDbUser(String dbUser) {
        AppData.dbUser = dbUser;
    }

    public static String getDbPass() {
        return dbPass;
    }

    public static void setDbPass(String dbPass) {
        AppData.dbPass = dbPass;
    }

    public static String getDbSchema() {
        return dbSchema;
    }

    public static void setDbSchema(String dbSchema) {
        AppData.dbSchema = dbSchema;
    }

    public static String getPathCSS() {
        return pathCSS;
    }

    public static void setPathCSS(String pathCSS) {
        AppData.pathCSS = pathCSS;
    }

    public static String getSQLDataBaseType() {
        return SQLDataBaseType;
    }

    public static void setSQLDataBaseType(String SQLDataBaseType) {
        AppData.SQLDataBaseType = SQLDataBaseType;
    }

    public static SQLDataBase getDb() {
        return db;
    }

    public static void setDb(SQLDataBase db) {
        AppData.db = db;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        AppData.filePath = filePath;
    }

    public static String getPathToSQLiteDB() {
        return pathToSQLiteDB;
    }

    public static void setPathToSQLiteDB(String pathToSQLiteDB) {
        AppData.pathToSQLiteDB = pathToSQLiteDB;
    }

    public static Window getOwner() {
        return owner;
    }

    public static void setOwner(Window owner) {
        AppData.owner = owner;
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

    public static void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

        stage.getIcons().add(new Image("resources/main.png"));

        alert.setTitle("RTTR-Master");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
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

    public static void openCustomWindow(ActionEvent event, Parent root, int width, int height, Modality modality, boolean isWindowResizable) {
        Stage stage = new Stage();
        stage.setTitle("RTTR-Master (Requests to Technics Repair)");
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(isWindowResizable);
        stage.getIcons().add(new Image("resources/main.png"));
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(getPathCSS());
        stage.setScene(scene);
        stage.initModality(modality);
        stage.initOwner(getOwner());
        stage.show();
    }

    public static void readSettingsFromFile() {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(filePath)))) {
            setSQLDataBaseType((String)in.readObject());
            setDbHost((String)in.readObject());
            setDbPort((String)in.readObject());
            setDbUser((String)in.readObject());
            setDbPass((String)in.readObject());
            setDbSchema((String)in.readObject());
            setPathToSQLiteDB((String)in.readObject());
            setPathCSS((String)in.readObject());

            System.out.println("File (" + new File(filePath).getAbsolutePath() + ") was loaded successfully.");
        } catch (IOException e) {
            System.out.println("Something wrong with the <" + filePath + ">: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        public static void writeDefaultSettingsToFile () {

            try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(filePath)))) {
                out.writeObject("SQLite");
//                out.writeObject("MySQL");
                out.writeObject("localhost");
                out.writeObject("3306");
                out.writeObject("root");
                out.writeObject("diamond");
                out.writeObject("rttr-base");
                out.writeObject("src/db/rttr-base.db");
                out.writeObject("/themes/BlueTheme.css");
                out.close();
                System.out.println("The file was successfully saved.");
            } catch (IOException e) {
                System.out.println("File can't be opened. Program terminates.");
                e.printStackTrace();
            }
        }
    }
