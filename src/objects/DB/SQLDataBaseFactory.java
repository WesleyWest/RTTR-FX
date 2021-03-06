package objects.DB;

import GUI.Settings.SettingsPaneController;
import javafx.fxml.FXMLLoader;
import objects.BL.AppData;
import objects.GUI.GUIData;

import java.sql.SQLException;


public class SQLDataBaseFactory extends AppData {

    public SQLDataBase getSQLDataBaseByType (String typeName) throws    SQLException,
                                                                        ClassNotFoundException,
                                                                        IllegalAccessException,
                                                                        InstantiationException {
        for (DBType type : GUIData.getDbTypes()) {
            if (type.getName().toUpperCase().equals(typeName.toUpperCase())) {
                    Class<?> dbClass = Class.forName("objects.DB.types."+type.getClassName());
                    SQLDataBase sqlDataBase = (SQLDataBase) dbClass.newInstance();
                    return sqlDataBase;
            }
        }
        return null;
    }

    public FXMLLoader getPaneByDBType(String typeName) {
        for (DBType type : GUIData.getDbTypes()) {
            if (type.getName().toUpperCase().equals(typeName.toUpperCase())) {
                return new FXMLLoader(getClass().getResource(type.getPathToFXML()));
            }
        }
        return null;
    }

    public SettingsPaneController getDBSettingsControllerByType(String typeName) {
        for (DBType type : GUIData.getDbTypes()) {
            if (type.getName().toUpperCase().equals(typeName.toUpperCase())) {
                try {
                    Class<?> controllerClass = Class.forName(type.getPaneControllerClassName());
                    return (SettingsPaneController) controllerClass.newInstance();
                } catch (ClassNotFoundException e) {
                    GUIData.showAlert("Class not found: "+e.getLocalizedMessage());
                } catch (InstantiationException e) {
                    GUIData.showAlert("Can't instant object: "+e.getLocalizedMessage());
                } catch (IllegalAccessException e) {
                    GUIData.showAlert("Illegal access exception: "+e.getLocalizedMessage());
                }
            }
        }
        return null;
    }

}
