package forms.Settings.DBSettingsPanes.MySQL;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import forms.Settings.SettingsController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import objects.AppData;

public class MySQLSettingsPaneController implements DBSettingsPaneController {
    @FXML
    private TextField mySQLHostField;

    @FXML
    private TextField mySQLPortField;

    @FXML
    private TextField mySQLSchemaField;

    @FXML
    private TextField mySQLLoginField;

    @FXML
    private TextField mySQLPasswordField;

    private TextField[] fields;

    private SettingsController parentController;


    @Override
    public void setInformation() {
        mySQLHostField.setText(AppData.getIniFile().get("MYSQL","Host"));
        mySQLPortField.setText(AppData.getIniFile().get("MYSQL","Port"));
        mySQLSchemaField.setText(AppData.getIniFile().get("MYSQL","Schema"));
        mySQLLoginField.setText(AppData.getIniFile().get("MYSQL","Login"));
        mySQLPasswordField.setText(AppData.getIniFile().get("MYSQL","Password"));
    }

    @Override
    public void getInformation() {

    }

    @Override
    public int getDataHash() {
        return 0;
    }

    @Override
    public void setParentController(SettingsController parentController) {
        this.parentController=parentController;
    }
}
