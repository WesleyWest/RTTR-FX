package objects.DB.types;

import javafx.collections.ObservableList;
import objects.AppData;
import objects.DB.SQLDataBase;
import objects.Users.User;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataBase extends SQLDataBase {
    private static String pathToFile = null;


    public SQLiteDataBase() {
        setLogin(getIniFile().get("SQLITE", "Login"));
        setPassword(getIniFile().get("SQLITE", "Password"));

        if (getPathToFile() == null) {
            setPathToFile(getIniFile().get("SQLITE", "Path to file"));
        }

        setConnectionString("jdbc:sqlite:" + getPathToFile());
        try {
            setConnection(DriverManager.getConnection(getConnectionString()));
        } catch (SQLException e) {
            AppData.showAlert("SQL exception: " + e.getLocalizedMessage());
        }

        setClassName("org.sqlite.JDBC");
        setPathToFile(null);
    }

    public static void setPathToFile(String pathToFile) {
        SQLiteDataBase.pathToFile = pathToFile;
    }

    public static String getPathToFile() {
        return pathToFile;
    }

}