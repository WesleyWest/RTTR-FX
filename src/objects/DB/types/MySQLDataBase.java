package objects.DB.types;

import objects.DB.SQLDataBase;

public class MySQLDataBase extends SQLDataBase {
    public MySQLDataBase() {
        String host = getIniFile().get("MYSQL","Host");
        String port = getIniFile().get("MYSQL","Port");
        String schema = getIniFile().get("MYSQL","Schema");
        setLogin(getIniFile().get("MYSQL","Login"));
        setPassword(getIniFile().get("MYSQL","Password"));

        setConnectionString("jdbc:mysql://" + host+ ":" + port + "/" + schema);
        setClassName("com.mysql.cj.jdbc.Driver");
    }
}
