package objects.DB;

public class MySQLDataBase extends SQLDataBase {
    public MySQLDataBase() {
        setConnectionString("jdbc:mysql://" + "localhost"+ ":" + "3306" + "/" + "rttr-base");
        setClassName("com.mysql.cj.jdbc.Driver");
    }
}
