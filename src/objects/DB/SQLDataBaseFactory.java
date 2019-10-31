package objects.DB;

public class SQLDataBaseFactory {
    public static SQLDataBase getSQLDataBaseByType(String type){
        switch (type){
            case "MySQL": return new MySQLDataBase();
            case "SQLite": return new SQLiteDataBase();
        }
        return null;
    }
}
