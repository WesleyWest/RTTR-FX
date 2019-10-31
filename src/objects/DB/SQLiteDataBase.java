package objects.DB;

import java.io.File;

public class SQLiteDataBase extends SQLDataBase {
    public SQLiteDataBase() {
        File file = new File(getPathToSQLiteDB());
        setConnectionString("jdbc:sqlite:"+file.getAbsolutePath());
        setClassName("org.sqlite.JDBC");
    }
}
