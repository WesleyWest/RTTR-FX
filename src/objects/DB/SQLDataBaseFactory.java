package objects.DB;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import javafx.fxml.FXMLLoader;
import objects.AppData;
import objects.DB.types.SQLiteDataBase;
import objects.DB.types.MySQLDataBase;
import objects.DBType;


public class SQLDataBaseFactory extends AppData {



    public SQLDataBase getSQLDataBaseByType(String typeName) {
        for (DBType type : getDbTypes()) {
            if (type.getName().toUpperCase().equals(typeName.toUpperCase())) {
                try {
                    Class<?> dbClass = Class.forName("objects.DB.types."+type.getClassName());
                    SQLDataBase sqlDataBase = (SQLDataBase) dbClass.newInstance();
                    return sqlDataBase;
                } catch (ClassNotFoundException e) {
                    showAlert("Class not found: "+e.getLocalizedMessage());
                } catch (InstantiationException e) {
                    showAlert("Can't instant object: "+e.getLocalizedMessage());
                } catch (IllegalAccessException e) {
                    showAlert("Illegal access exception: "+e.getLocalizedMessage());
                }
            }
        }
        return null;
    }

    public FXMLLoader getPaneByDBType(String typeName) {
        for (DBType type : getDbTypes()) {
            if (type.getName().toUpperCase().equals(typeName.toUpperCase())) {
                return new FXMLLoader(getClass().getResource(type.getPathToFXML()));
            }
        }
        return null;
    }

    public DBSettingsPaneController getDBSettingsControllerByType(String typeName) {
        for (DBType type : getDbTypes()) {
            if (type.getName().toUpperCase().equals(typeName.toUpperCase())) {
                try {
                    Class<?> controllerClass = Class.forName(type.getPaneControllerClassName());
                    return (DBSettingsPaneController) controllerClass.newInstance();
                } catch (Exception e) {
                    showAlert(e.getLocalizedMessage());
                }
            }
        }
        return null;
    }

}
