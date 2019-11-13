package forms.Settings.DBSettingsPanes.SQLite;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import javafx.event.ActionEvent;
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

public class SQLiteSettingsPaneController extends DBSettingsPaneController {

    @FXML
    private TextField sqLitePathTODBField;

    @FXML
    private Button openFileDialogButton;

    @FXML
    private Button testConnectionButton;

    @FXML
    private Label connectionStatusLabel;


    @FXML
    void openFileDialog(ActionEvent event) {
        connectionStatusLabel.setText("");
        FileChooser fileChooser = new FileChooser();
        File file = new File((sqLitePathTODBField.getText()));
        if (file.exists()) {
            fileChooser.setInitialDirectory(file.getParentFile());
            fileChooser.setInitialFileName(file.getName());
        } else {
            AppData.showAlert("Файл \n"+file.getAbsolutePath()+"\nне найден.");
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
        String tmpPathToFile = SQLiteDataBase.getPathToFile();
        SQLiteDataBase.setPathToFile(sqLitePathTODBField.getText());
        SQLiteDataBase testSQLiteDB = new SQLiteDataBase();
        if (testSQLiteDB.testConnection()) {
            connectionStatusLabel.setTextFill(Paint.valueOf("green"));
            connectionStatusLabel.setText("Соединение успешно установлено.");
        } else {
            connectionStatusLabel.setTextFill(Paint.valueOf("red"));
            connectionStatusLabel.setText("Соединение установить не удалось.");
        }
        SQLiteDataBase.setPathToFile(tmpPathToFile);
    }

    @Override
    public void setInformation() {
        String path = AppData.getIniFile().get("SQLITE", "Path to file");
        File file = new File(path);
        sqLitePathTODBField.setText(file.getAbsolutePath());
    }

    @Override
    public void saveInformation() {

    }

    @Override
    public int getDataHash() {
        int tmpHash = sqLitePathTODBField.getText().hashCode();
        return tmpHash;
    }

}
