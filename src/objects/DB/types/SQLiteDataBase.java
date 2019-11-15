package objects.DB.types;

import objects.DB.SQLDataBase;
import objects.GUI.RTTRApp;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataBase extends SQLDataBase {
    public SQLiteDataBase() throws SQLException {
        String pathToFile= RTTRApp.getIniFile().get("SQLITE","Path to file");
        setConnectionString("jdbc:sqlite:" + pathToFile);

            setConnection(DriverManager.getConnection(getConnectionString()));

        setClassName("org.sqlite.JDBC");
    }
}
