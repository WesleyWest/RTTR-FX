package forms.Settings;

import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    @FXML
    private AnchorPane headerAnchorPane;
    @FXML
    private Label headerLabelBig;
    @FXML
    private Label headerLabelSmall;

    private SQLDataBaseFactory factory;
    Pane dbSettingsPane = null;
    DBSettingsPaneController dbSettingsPaneController = null;
    ArrayList<String> dbTypesStr, themesStr;
    private int referenceDataHash, modifiedDataHash;

    @FXML
    void initialize() {

        factory = new SQLDataBaseFactory();

        setChooseComboBoxValues();
        setThemesComboBoxValues();
        setDBSettingsPane();
        referenceDataHash = createHash();

        applyCSS();
    }

    void applyCSS() {
        AnchorPane[] panes = {settingsAnchorPane, usersAnchorPane, divisionsAnchorPane, employeesAnchorPane, technicAnchorPane};
        for (AnchorPane pane : panes) {
            pane.getStyleClass().add(0, "anchor-pane-in-tab");
        }
        headerLabelBig.getStyleClass().set(0, "label-header-big");
        headerLabelSmall.getStyleClass().set(0, "label-header-small");
        headerAnchorPane.getStyleClass().add("anchor-pane-header");
    }

    @FXML
    void chooseDBComboBoxClick(ActionEvent event) {
        AppData.setActiveSQLDataBaseType((String) chooseDBComboBox.getSelectionModel().getSelectedItem());
        setDBSettingsPane();
        calcModifiedDataHash();
        checkHashes();
    }

    @FXML
    void exitButtonClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    void themeComboBoxClick(ActionEvent event) {
        calcModifiedDataHash();
        checkHashes();
    }

    @FXML
    void applyButtonClick(ActionEvent event) {
        applyChanges(event);
    }

    void applyChanges(ActionEvent event) {
        System.out.println(isColorThemeToChange());
        if (isColorThemeToChange()) {
            changeColorTheme(event);
        }
    }

    private boolean isColorThemeToChange() {
        return !(themeComboBox.getSelectionModel().getSelectedItem().equals(AppData.getThemeName()));
    }

    private void changeColorTheme(ActionEvent event) {
        String newTheme = themeComboBox.getSelectionModel().getSelectedItem().toString();

        AppData.getIniFile().put("MAIN", "Active theme",newTheme);
        try {
            AppData.getIniFile().store();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String newPath=ColorTheme.getPathbyName(AppData.getThemes(),newTheme);

        AppData.setThemeName(newTheme);
        AppData.setPathCSS(newPath);

        Node source = (Node) event.getSource();
        Scene currentScene = source.getScene();
        currentScene.getStylesheets().set(0,AppData.getPathCSS());
        referenceDataHash=createHash();
    }

    private void setThemesComboBoxValues() {
        themesStr = new ArrayList<>();
        for (ColorTheme theme : AppData.getThemes()) {
            themesStr.add(theme.getName());
        }
        themeComboBox.setItems(FXCollections.observableArrayList(themesStr));
        themeComboBox.getSelectionModel().select(
                themeComboBox.getItems().indexOf(AppData.getThemeName())
        );
    }

    private void setChooseComboBoxValues() {
        dbTypesStr = new ArrayList<>();
        for (DBType type : AppData.getDbTypes()) {
            dbTypesStr.add(type.getName());
        }
        chooseDBComboBox.setItems(FXCollections.observableArrayList(dbTypesStr));
        chooseDBComboBox.getSelectionModel().select(
                chooseDBComboBox.getItems().indexOf(AppData.getActiveSQLDataBaseType())
        );
    }

    private void setDBSettingsPane() {
        if (settingsAnchorPane.getChildren().indexOf(dbSettingsPane) != 0) {
            settingsAnchorPane.getChildren().remove(dbSettingsPane);
        }

        FXMLLoader loader = factory.getPaneByDBType(AppData.getActiveSQLDataBaseType());
        try {
            dbSettingsPane = loader.load();
            dbSettingsPaneController = loader.getController();
            dbSettingsPaneController.setParentController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (dbSettingsPane != null) {
            settingsAnchorPane.getChildren().add(dbSettingsPane);
            dbSettingsPane.setLayoutX(14);
            dbSettingsPane.setLayoutY(70);
            dbSettingsPaneController.setInformation();
        }
    }


    int createHash() {
        return themeComboBox.getSelectionModel().getSelectedItem().hashCode() +
                chooseDBComboBox.getSelectionModel().getSelectedItem().hashCode() +
                dbSettingsPaneController.getDataHash();
    }

    public void calcModifiedDataHash() {
        modifiedDataHash = createHash();
    }

    public void checkHashes() {
        if (referenceDataHash != modifiedDataHash) {
            applyButton.setDisable(false);
            OKButton.setDisable(false);
        } else {
            applyButton.setDisable(true);
            OKButton.setDisable(true);
        }
    }


}
