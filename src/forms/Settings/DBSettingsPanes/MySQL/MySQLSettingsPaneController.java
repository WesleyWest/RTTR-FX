package forms.Settings.DBSettingsPanes.MySQL;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        fields = new TextField[]
               {mySQLHostTextField,
                mySQLPortTextField,
                mySQLSchemaTextField,
                mySQLLoginTextField,
                mySQLPasswordTextField};
    }

    @Override
    public void setInformation(String info) {


        String value = "";
        int count = 0;
        for (int i = 0; i < info.length(); i++) {
            if (info.charAt(i) != '&') {
                value+=info.charAt(i);
            } else {
                fields[count].setText(value);
                count++;
                value="";
            }

        }

    }

    @Override
    public String getInformation() {
        return null;
    }
}
