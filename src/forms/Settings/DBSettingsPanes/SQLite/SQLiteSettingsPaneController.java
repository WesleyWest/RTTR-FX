package forms.Settings.DBSettingsPanes.SQLite;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import objects.AppData;
import objects.DB.types.SQLiteDataBase;

import java.io.File;
import java.sql.SQLException;

public class SQLiteSettingsPaneController implements DBSettingsPaneController {

    @FXML
    private TextField sqLitePathTODBField;

    @FXML
    private TextField sqLiteLoginField;

    @FXML
    private TextField sqLitePasswordField;

    @FXML
    private Button openFileDialogButton;

    @FXML
    private Button testConnectionButton;

    @FXML
    private Label connectionStatusLabel;


    @FXML
    void openFileDialog(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        File file = new File((sqLitePathTODBField.getText()));
        fileChooser.setInitialDirectory(file.getParentFile());
        fileChooser.setInitialFileName(file.getName());

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();

        File sqLiteDBFile = fileChooser.showOpenDialog(stage);
        if (sqLiteDBFile != null) {
            sqLitePathTODBField.setText(sqLiteDBFile.getAbsolutePath());
        }
    }

    @FXML
    void testConnection(ActionEvent event) {
        SQLiteDataBase.setPathToFile(sqLitePathTODBField.getText());
        SQLiteDataBase testSQLiteDB = new SQLiteDataBase();
        try {
            testSQLiteDB.open();
            connectionStatusLabel.setTextFill(Paint.valueOf("green"));
            connectionStatusLabel.setText("Соединение успешно установлено.");
            testSQLiteDB.close();
        } catch (Exception e) {
            connectionStatusLabel.setTextFill(Paint.valueOf("red"));
            connectionStatusLabel.setText("Соединение установить не удалось.");
        }
    }

    @Override
    public void setInformation() {
        String path = AppData.getIniFile().get("SQLITE", "Path to file");
        File file = new File(path);
        sqLitePathTODBField.setText(file.getAbsolutePath());
        sqLiteLoginField.setText(AppData.getIniFile().get("SQLITE", "Login"));
        sqLitePasswordField.setText(AppData.getIniFile().get("SQLITE", "Password"));
    }

    @Override
    public void getInformation() {

    }
}
