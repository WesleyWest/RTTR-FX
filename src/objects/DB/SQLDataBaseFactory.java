package objects.DB;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import forms.Settings.DBSettingsPanes.MySQL.MySQLSettingsPaneController;
import forms.Settings.DBSettingsPanes.SQLite.SQLiteSettingsPaneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;

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

    public FXMLLoader getPaneByDBType(String type) {
        switch (type) {
            case "MySQL":
                return new FXMLLoader(getClass().getResource("../../forms/Settings/DBSettingsPanes/MySQL/MySQLSettingsPane.fxml"));
            case "SQLite":
                return new FXMLLoader(getClass().getResource("../../forms/Settings/DBSettingsPanes/SQLite/SQLiteSettingsPane.fxml"));
        }
        return null;
    }

    public DBSettingsPaneController getDBSettingsControllerByType(String type) {

        switch (type) {
            case "MySQL":
                return new MySQLSettingsPaneController();
            case "SQLite":
                return new SQLiteSettingsPaneController();
        }
        return null;
    }
}
