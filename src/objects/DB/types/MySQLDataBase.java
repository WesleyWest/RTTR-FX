package objects.DB.types;

import objects.DB.SQLDataBase;
import objects.GUI.GUIData;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDataBase extends SQLDataBase {
    public MySQLDataBase() throws SQLException {
        String host = GUIData.getIniFile().get("MYSQL","Host");
        String port = GUIData.getIniFile().get("MYSQL","Port");
        String schema = GUIData.getIniFile().get("MYSQL","Schema");
        setLogin(GUIData.getIniFile().get("MYSQL","Login"));
        setPassword(GUIData.getIniFile().get("MYSQL","Password"));
        setConnectionString("jdbc:mysql://" + host+ ":" + port + "/" + schema);

        try {
            setConnection(DriverManager.getConnection(getConnectionString(),getLogin(),getPassword()));
        } catch (SQLException e) {
            GUIData.showAlert("MySQL exception by connection: " + e.getLocalizedMessage());
            throw new SQLException();
        }
        setClassName("com.mysql.cj.jdbc.Driver");
    }

    @Override
    public int getLastSequenceNumber(String tableName) {
        String query =
                "SELECT auto_increment FROM information_schema.tables WHERE table_name='"+tableName+"';";
        return findLastSequenceNumber(query);
    }
}
