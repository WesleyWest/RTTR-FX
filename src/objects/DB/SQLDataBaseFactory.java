package objects.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class SQLDataBaseFactory {
    public static SQLDataBase getSQLDataBaseByType(String type){
        switch (type){
            case "MySQL": return new MySQLDataBase();
            case "SQLite": return new SQLiteDataBase();
        }
        return null;
    }

    public static ObservableList<String> getDBTypesList(){
        List<String> result = new ArrayList<>();
        result.add("MySQL");
        result.add("SQLite");
        return FXCollections.observableArrayList(result);
    }


}
