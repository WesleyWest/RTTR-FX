package forms.Settings;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.AppData;
import objects.ColorTheme;
import objects.DB.SQLDataBaseFactory;
import objects.DBType;

import java.io.IOException;
import java.util.ArrayList;

public class SettingsController {

    @FXML
    private ComboBox chooseDBComboBox;

    @FXML
    private ComboBox themeComboBox;

    @FXML
    private Button exitButton;

    @FXML
    private Button applyButton;

    @FXML
    private Button OKButton;

    @FXML
    private AnchorPane settingsAnchorPane;
    @FXML
    private AnchorPane usersAnchorPane;
    @FXML
    private AnchorPane divisionsAnchorPane;
    @FXML
    private AnchorPane employeesAnchorPane;
    @FXML
    private AnchorPane technicAnchorPane;

    private SQLDataBaseFactory factory;
    Pane dbSettingsPane = null;
    DBSettingsPaneController controller = null;
    ArrayList<String> dbTypesStr;
    ArrayList<String> themesStr;

    @FXML
    void initialize() {
        AnchorPane[] panes = {settingsAnchorPane, usersAnchorPane, divisionsAnchorPane, employeesAnchorPane, technicAnchorPane};
        for (AnchorPane pane : panes) {
            pane.getStyleClass().add(0, "anchor-pane-in-tab");
        }

        factory = new SQLDataBaseFactory();

        dbTypesStr = new ArrayList<String>();
        for (DBType type : AppData.getDbTypes()) {
            dbTypesStr.add(type.getName());
        }
        chooseDBComboBox.setItems(FXCollections.observableArrayList(dbTypesStr));
        chooseDBComboBox.getSelectionModel().select(
                chooseDBComboBox.getItems().indexOf(AppData.getActiveSQLDataBaseType())
        );

        themesStr = new ArrayList<String>();
        for(ColorTheme theme: AppData.getThemes()){
            themesStr.add(theme.getName());
        }
        themeComboBox.setItems(FXCollections.observableArrayList(themesStr));
        themeComboBox.getSelectionModel().select(
                themeComboBox.getItems().indexOf(AppData.getThemeName())
        );


        setDBSettingsPane();
    }

    private void setDBSettingsPane() {
        if (settingsAnchorPane.getChildren().indexOf(dbSettingsPane) != 0) {
            settingsAnchorPane.getChildren().remove(dbSettingsPane);
        }
        FXMLLoader loader = factory.getPaneByDBType(AppData.getActiveSQLDataBaseType());
        try {
            dbSettingsPane = loader.load();
            controller = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (dbSettingsPane != null) {
            settingsAnchorPane.getChildren().add(dbSettingsPane);
            dbSettingsPane.setLayoutX(14);
            dbSettingsPane.setLayoutY(70);
            controller.setInformation();
        }
    }

    @FXML
    void chooseDBComboBoxClick(ActionEvent event) {
        AppData.setActiveSQLDataBaseType((String) chooseDBComboBox.getSelectionModel().getSelectedItem());
        setDBSettingsPane();
    }

    @FXML
    void exitButtonClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }


}