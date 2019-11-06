package forms.Settings.DBSettingsPanes.MySQL;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import objects.AppData;

public class MySQLSettingsPaneController implements DBSettingsPaneController {
    @FXML
    private TextField mySQLHostTextField;

    @FXML
    private TextField mySQLPortTextField;

    @FXML
    private TextField mySQLSchemaTextField;

    @FXML
    private TextField mySQLLoginTextField;

    @FXML
    private PasswordField mySQLPasswordTextField;

    private TextField[] fields;


    @FXML
    void initialize() {
    }

    @Override
    public void setInformation() {
        mySQLHostTextField.setText(AppData.getIniFile().get("MYSQL","Host"));
        mySQLPortTextField.setText(AppData.getIniFile().get("MYSQL","Port"));
        mySQLSchemaTextField.setText(AppData.getIniFile().get("MYSQL","Schema"));
        mySQLLoginTextField.setText(AppData.getIniFile().get("MYSQL","Login"));
        mySQLPasswordTextField.setText(AppData.getIniFile().get("MYSQL","Password"));
    }

    @Override
    public void getInformation() {

    }
}
