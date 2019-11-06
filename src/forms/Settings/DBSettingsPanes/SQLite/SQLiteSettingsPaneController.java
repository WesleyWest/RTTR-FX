package forms.Settings.DBSettingsPanes.SQLite;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import objects.AppData;

public class SQLiteSettingsPaneController implements DBSettingsPaneController {

    @FXML
    private TextField sqLitePathTODBTextField;

    @Override
    public void setInformation() {
        sqLitePathTODBTextField.setText(AppData.getIniFile().get("SQLITE","Path to file"));
    }

    @Override
    public void getInformation() {

    }
}
