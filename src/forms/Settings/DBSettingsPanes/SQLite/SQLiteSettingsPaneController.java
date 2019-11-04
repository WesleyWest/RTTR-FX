package forms.Settings.DBSettingsPanes.SQLite;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class SQLiteSettingsPaneController implements DBSettingsPaneController {

    @FXML
    private TextField SQLitePathTODBTextField;

    @Override
    public void setInformation(String info) {
        SQLitePathTODBTextField.setText(info);
    }

    @Override
    public String getInformation() {
        return null;
    }
}
