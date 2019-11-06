package objects.DB.types;

import objects.DB.SQLDataBase;

public class SQLiteDataBase extends SQLDataBase {

    public SQLiteDataBase() {
        setLogin(getIniFile().get("SQLite","Login"));
        setPassword(getIniFile().get("SQLite","Password"));

        setConnectionString("jdbc:sqlite:"+getIniFile().get("SQLITE","Path to file"));
        setClassName("org.sqlite.JDBC");
    }

}
