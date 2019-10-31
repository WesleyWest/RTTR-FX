package objects.DB;

public class MySQLDataBase extends SQLDataBase {
    public MySQLDataBase() {
        setConnectionString("jdbc:mysql://" + getDbHost() + ":" + getDbPort() + "/" + getDbSchema());
        setClassName("com.mysql.cj.jdbc.Driver");
    }
}
