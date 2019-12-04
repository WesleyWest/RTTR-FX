package GUI.Settings.DBSettingsPanes.SQLite;

import GUI.Settings.SettingsPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.DB.types.SQLiteDataBase;
import objects.GUI.GUIData;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class SQLiteSettingsPaneController extends SettingsPaneController {

    @FXML
    private TextField sqLitePathTODBField;

    @FXML
    private Button openFileDialogButton;

    @FXML
    private Button testConnectionButton;

    @FXML
    private Label connectionStatusLabel;

    String currentPath;


    @FXML
    void openFileDialog(ActionEvent event) {
        connectionStatusLabel.setText("");
        FileChooser fileChooser = new FileChooser();
        File file = new File((sqLitePathTODBField.getText()));
        if (file.exists()) {
            fileChooser.setInitialDirectory(file.getParentFile());
            fileChooser.setInitialFileName(file.getName());
        } else {
            GUIData.showAlert("Файл \n" + file.getAbsolutePath() + "\nне найден.");
        }

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        File sqLiteDBFile = fileChooser.showOpenDialog(stage);

        if (sqLiteDBFile != null) {
            sqLitePathTODBField.setText(sqLiteDBFile.getAbsolutePath());
        }

        getParentController().calcModifiedDataHash();
        getParentController().checkHashes();
    }

    @FXML
    void testButtonClick(ActionEvent event) {
        currentPath = getCurrentPath();
        saveTempSettingsIntoFile();
        SQLiteDataBase testSQLiteDB = null;
        try {
            testSQLiteDB = new SQLiteDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (testSQLiteDB.testConnection()) {
            connectionStatusLabel.setTextFill(Paint.valueOf("green"));
            connectionStatusLabel.setText("Соединение успешно установлено.");
        } else {
            connectionStatusLabel.setTextFill(Paint.valueOf("red"));
            connectionStatusLabel.setText("Соединение установить не удалось.");
        }
        restoreCurrentSettingsIntoFile();
    }

    private String getCurrentPath () {
        return GUIData.getIniFile().get("SQLITE", "Path to file");
    }
    private void restoreCurrentSettingsIntoFile() {
        GUIData.getIniFile().put("SQLITE", "Path to file", currentPath);
    }

    private void saveTempSettingsIntoFile() {
        GUIData.getIniFile().put("SQLITE", "Path to file", sqLitePathTODBField.getText());
    }

    @Override
    public void setInformation() {
        String path = GUIData.getIniFile().get("SQLITE", "Path to file");
        File file = new File(path);
        sqLitePathTODBField.setText(file.getAbsolutePath());
    }

    @Override
    public void saveInformationToIni() {
        Wini ini = GUIData.getIniFile();
        ini.put("MAIN", "Active DB type", "SQLite");
        ini.put("SQLITE", "Path to file", sqLitePathTODBField.getText());
        try {
            ini.store();
        } catch (IOException e) {
            GUIData.showAlert(e.getLocalizedMessage());
        }
    }

    @Override
    public int getDataHash() {
        int tmpHash = sqLitePathTODBField.getText().hashCode();
        return tmpHash;
    }

}
