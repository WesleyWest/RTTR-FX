package objects.DB.types;

import objects.AppData;
import objects.DB.SQLDataBase;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDataBase extends SQLDataBase {
    public MySQLDataBase() throws SQLException {
        String host = getIniFile().get("MYSQL","Host");
        String port = getIniFile().get("MYSQL","Port");
        String schema = getIniFile().get("MYSQL","Schema");
        setLogin(getIniFile().get("MYSQL","Login"));
        setPassword(getIniFile().get("MYSQL","Password"));
        setConnectionString("jdbc:mysql://" + host+ ":" + port + "/" + schema);

        try {
            setConnection(DriverManager.getConnection(getConnectionString(),getLogin(),getPassword()));
        } catch (SQLException e) {
            AppData.showAlert("SQL exception by connection: " + e.getLocalizedMessage());
            throw new SQLException();
        }
        setClassName("com.mysql.cj.jdbc.Driver");
    }
}
