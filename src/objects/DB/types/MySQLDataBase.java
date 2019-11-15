package objects.DB.types;

import objects.DB.SQLDataBase;
import objects.GUI.RTTRApp;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDataBase extends SQLDataBase {
    public MySQLDataBase() throws SQLException {
        String host = RTTRApp.getIniFile().get("MYSQL","Host");
        String port = RTTRApp.getIniFile().get("MYSQL","Port");
        String schema = RTTRApp.getIniFile().get("MYSQL","Schema");
        setLogin(RTTRApp.getIniFile().get("MYSQL","Login"));
        setPassword(RTTRApp.getIniFile().get("MYSQL","Password"));
        setConnectionString("jdbc:mysql://" + host+ ":" + port + "/" + schema);

        try {
            setConnection(DriverManager.getConnection(getConnectionString(),getLogin(),getPassword()));
        } catch (SQLException e) {
            RTTRApp.showAlert("MySQL exception by connection: " + e.getLocalizedMessage());
            throw new SQLException();
        }
        setClassName("com.mysql.cj.jdbc.Driver");
    }
}
