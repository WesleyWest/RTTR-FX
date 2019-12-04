package GUI.Settings.DBSettingsPanes.MySQL;

import GUI.Settings.SettingsPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import objects.DB.SQLDataBase;
import objects.DB.types.MySQLDataBase;
import objects.GUI.GUIData;

import java.io.IOException;
import java.util.ArrayList;

public class MySQLSettingsPaneController extends SettingsPaneController {
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
        GUIData.getIniFile().put("MYSQL", "Host", host);
        GUIData.getIniFile().put("MYSQL", "Port", port);
        GUIData.getIniFile().put("MYSQL", "Schema", schema);
        GUIData.getIniFile().put("MYSQL", "Login", login);
        GUIData.getIniFile().put("MYSQL", "Password", password);
    }

    private void saveTempSettingsIntoFile() {
        GUIData.getIniFile().put("MYSQL", "Host", mySQLHostField.getText());
        GUIData.getIniFile().put("MYSQL", "Port", mySQLPortField.getText());
        GUIData.getIniFile().put("MYSQL", "Schema", mySQLSchemaField.getText());
        GUIData.getIniFile().put("MYSQL", "Login", mySQLLoginField.getText());
        GUIData.getIniFile().put("MYSQL", "Password", mySQLPasswordField.getText());
    }

    @Override
    public void setInformation() {
        host = GUIData.getIniFile().get("MYSQL", "Host");
        port = GUIData.getIniFile().get("MYSQL", "Port");
        schema = GUIData.getIniFile().get("MYSQL", "Schema");
        login = GUIData.getIniFile().get("MYSQL", "Login");
        password = GUIData.getIniFile().get("MYSQL", "Password");

        mySQLHostField.setText(host);
        mySQLPortField.setText(port);
        mySQLSchemaField.setText(schema);
        mySQLLoginField.setText(login);
        mySQLPasswordField.setText(password);
    }

    @Override
    public void saveInformationToIni() {
        GUIData.getIniFile().put("MAIN","Active DB type","MySQL");
        saveTempSettingsIntoFile();
        try {
            GUIData.getIniFile().store();
        } catch (IOException e) {
            GUIData.showAlert(e.getLocalizedMessage());
        }
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
