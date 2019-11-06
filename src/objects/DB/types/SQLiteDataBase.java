package objects.DB.types;

import objects.DB.SQLDataBase;

public class SQLiteDataBase extends SQLDataBase {
    private static String pathToFile=null;

    public static void setPathToFile(String pathToFile) {
        SQLiteDataBase.pathToFile = pathToFile;
    }

    public static String getPathToFile() {
        return pathToFile;
    }

    public SQLiteDataBase() {
        setLogin(getIniFile().get("SQLITE","Login"));
        setPassword(getIniFile().get("SQLITE","Password"));

        if (getPathToFile()==null){
            setPathToFile(getIniFile().get("SQLITE","Path to file"));
        }
        setConnectionString("jdbc:sqlite:"+getPathToFile());
        System.out.println("jdbc:sqlite:"+getPathToFile());
        setClassName("org.sqlite.JDBC");
        setPathToFile(null);
    }

}
