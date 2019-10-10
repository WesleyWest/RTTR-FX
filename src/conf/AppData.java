package conf;

import objects.MySQLDataBase;
import objects.User;

public class AppData {
    private static User user;
    private static MySQLDataBase db;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        AppData.user = user;
    }

    public static MySQLDataBase getDb() {
        return db;
    }

    public static void setDb(MySQLDataBase db) {
        AppData.db = db;
    }
}
