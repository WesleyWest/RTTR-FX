package objects.DB.types;

import objects.DB.SQLDataBase;
import objects.GUI.GUIData;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataBase extends SQLDataBase {
    public SQLiteDataBase() throws SQLException {
        String pathToFile = GUIData.getIniFile().get("SQLITE", "Path to file");
        setConnectionString("jdbc:sqlite:" + pathToFile);
        setConnection(DriverManager.getConnection(getConnectionString()));
        setClassName("org.sqlite.JDBC");
    }

    @Override
    public int getLastSequenceNumber(String tableName) {
        String query =
                "SELECT seq FROM SQLITE_SEQUENCE WHERE name = '" + tableName + "' LIMIT 1;";
        return findLastSequenceNumber(query) + 1;
    }

    @Override
    public void createTable(String tableName) {
        String query="";
        if (tableName.equals("employee_positions")){
             query = "CREATE TABLE employee_positions (\n" +
                     "                  position_id             INTEGER         NOT NULL\n" +
                     "                                                          PRIMARY KEY AUTOINCREMENT\n" +
                     "                                                          UNIQUE,\n" +

                     "                  position_description    VARCHAR (128)   DEFAULT NULL,\n" +
                     "                  position_weight         INT,\n"+
                     "                  position_isdeleted      BOOLEAN         NOT NULL\n" +
                     "                                                          DEFAULT (0));";
        }

        if (!query.equals("")) this.executeUpdateDB(query);
    }
}
