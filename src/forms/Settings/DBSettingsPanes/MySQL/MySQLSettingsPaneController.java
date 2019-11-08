package forms.Settings.DBSettingsPanes.MySQL;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import objects.AppData;
import objects.DB.SQLDataBase;
import objects.DB.types.MySQLDataBase;

import java.util.ArrayList;

public class MySQLSettingsPaneController extends DBSettingsPaneController {
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

    @FXML
    private Button testButton;

    @FXML
    private Label connectionStatusLabel;

    private ArrayList<TextField> fields;

    String host;
    String port;
    String schema;
    String login;
    String password;

    @FXML
    void initialize() {
        fields = new ArrayList<>();
        fields.add(mySQLHostField);
        fields.add(mySQLPortField);
        fields.add(mySQLSchemaField);
        fields.add(mySQLLoginField);
        fields.add(mySQLPasswordField);

        for (TextField field : fields) {
            field.focusedProperty().addListener((ov, oldV, newV) -> {
                if (!newV) {
                    getParentController().calcModifiedDataHash();
                    getParentController().checkHashes();
                    connectionStatusLabel.setText("");
                }
            });
        }
    }

    @FXML
    void testButtonClick(ActionEvent event) {
        saveTempSettingsIntoFile();
        boolean result = false;
        SQLDataBase testMySQLDataBase = null;
        try {
            testMySQLDataBase = new MySQLDataBase();
            if (testMySQLDataBase.testConnection()) {
                result = true;
            }
        } catch (Exception e) {
            result = false;
        }

        if (result) {
            connectionStatusLabel.setTextFill(Paint.valueOf("green"));
            connectionStatusLabel.setText("Соединение успешно установлено.");
        } else {
            connectionStatusLabel.setTextFill(Paint.valueOf("red"));
            connectionStatusLabel.setText("Соединение установить не удалось.");
        }

        restoreCurrentSettingsIntoFile();
    }

    private void restoreCurrentSettingsIntoFile() {
        AppData.getIniFile().put("MYSQL", "Host", host);
        AppData.getIniFile().put("MYSQL", "Port", port);
        AppData.getIniFile().put("MYSQL", "Schema", schema);
        AppData.getIniFile().put("MYSQL", "Login", login);
        AppData.getIniFile().put("MYSQL", "Password", password);

//        AppData.getIniFile().put("MYSQL","Host","localhost");
//        AppData.getIniFile().put("MYSQL","Port","3306");
//        AppData.getIniFile().put("MYSQL","Schema","rttr-base");
//        AppData.getIniFile().put("MYSQL","Login","root");
//        AppData.getIniFile().put("MYSQL","Password","diamond");
    }

    private void saveTempSettingsIntoFile() {
        AppData.getIniFile().put("MYSQL", "Host", mySQLHostField.getText());
        AppData.getIniFile().put("MYSQL", "Port", mySQLPortField.getText());
        AppData.getIniFile().put("MYSQL", "Schema", mySQLSchemaField.getText());
        AppData.getIniFile().put("MYSQL", "Login", mySQLLoginField.getText());
        AppData.getIniFile().put("MYSQL", "Password", mySQLPasswordField.getText());
    }

    @Override
    public void setInformation() {
        host = AppData.getIniFile().get("MYSQL", "Host");
        port = AppData.getIniFile().get("MYSQL", "Port");
        schema = AppData.getIniFile().get("MYSQL", "Schema");
        login = AppData.getIniFile().get("MYSQL", "Login");
        password = AppData.getIniFile().get("MYSQL", "Password");

        mySQLHostField.setText(host);
        mySQLPortField.setText(port);
        mySQLSchemaField.setText(schema);
        mySQLLoginField.setText(login);
        mySQLPasswordField.setText(password);
    }

    @Override
    public void saveInformation() {

    }

    @Override
    public int getDataHash() {
        int tmpHash = 0;
        for (TextField field : fields) {
            tmpHash += field.getText().hashCode();
        }
        return tmpHash;
    }
}
