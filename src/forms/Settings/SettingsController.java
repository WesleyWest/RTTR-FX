package forms.Settings;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
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
import objects.DB.SQLDataBaseFactory;

import java.io.IOException;

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

    @FXML
    void initialize() {
        AnchorPane[] panes = {settingsAnchorPane, usersAnchorPane, divisionsAnchorPane, employeesAnchorPane, technicAnchorPane};
        for (AnchorPane pane : panes) {
            pane.getStyleClass().add(0, "anchor-pane-in-tab");
        }
        factory = new SQLDataBaseFactory();
        chooseDBComboBox.setItems(factory.getDBTypesList());
        chooseDBComboBox.getSelectionModel().select(
                chooseDBComboBox.getItems().indexOf(AppData.getSQLDataBaseType())
        );

        setDBSettingsPane();


    }

    private void setDBSettingsPane() {
        if (settingsAnchorPane.getChildren().indexOf(dbSettingsPane) != 0) {
            settingsAnchorPane.getChildren().remove(dbSettingsPane);
        }
        FXMLLoader loader = factory.getPaneByDBType(AppData.getSQLDataBaseType());
        try {
            dbSettingsPane = loader.load();
            controller = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (dbSettingsPane != null) {
            settingsAnchorPane.getChildren().add(dbSettingsPane);
            dbSettingsPane.setLayoutX(14);
            dbSettingsPane.setLayoutY(70);}
    }

    @FXML
    void chooseDBComboBoxClick(ActionEvent event) {
        AppData.setSQLDataBaseType((String) chooseDBComboBox.getSelectionModel().getSelectedItem());
        setDBSettingsPane();
    }

    @FXML
    void exitButtonClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }


}