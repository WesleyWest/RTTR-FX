package objects.DB;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import objects.AppData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SQLDataBaseFactory {

    public SQLDataBase getSQLDataBaseByType(String type) {
        switch (type) {
            case "MySQL":
                return new MySQLDataBase();
            case "SQLite":
                return new SQLiteDataBase();
        }
        return null;
    }

    public ObservableList<String> getDBTypesList() {
        List<String> result = new ArrayList<>();
        result.add("MySQL");
        result.add("SQLite");
        return FXCollections.observableArrayList(result);
    }

    public Pane getPaneByDBType(String type) {
        try {
            switch (type) {
                case "MySQL":
                    return (Pane) FXMLLoader.load(getClass().getResource("../../forms/Settings/SettingsPaneMySQL.fxml"));
                case "SQLite":
                    return (Pane) FXMLLoader.load(getClass().getResource("../../forms/Settings/SettingsPaneSQLite.fxml"));
            }

        } catch (IOException e) {
            AppData.showAlert("IOException: " + e.getLocalizedMessage());
        }
        return null;
    }
}
