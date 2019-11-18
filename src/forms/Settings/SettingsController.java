package forms.Settings;

import forms.GUIController;
import forms.Settings.DBSettingsPanes.DBSettingsPaneController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.GUI.ColorTheme;
import objects.DB.SQLDataBaseFactory;
import objects.DB.DBType;
import objects.GUI.GUIData;

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
    private Tab settingsTab;
    @FXML
    private Tab usersTab;
    @FXML
    private Tab divisionsTab;
    @FXML
    private Tab employeesTab;
    @FXML
    private Tab technicTab;
    @FXML
    private Label headerLabelBig;
    @FXML
    private Label headerLabelSmall;

    private SQLDataBaseFactory factory;
    Pane dbSettingsPane = null;
    DBSettingsPaneController dbSettingsPaneController = null;
    ArrayList<String> dbTypesStr, themesStr;
    private int referenceDataHash, modifiedDataHash, referenceDBSettingsHash;
    private GUIController parentController;

    @FXML
    void initialize() {
        if (GUIData.getSettingsWindowCaller().equals("settingsButton")){
            usersTab.setDisable(true);
            divisionsTab.setDisable(true);
            employeesTab.setDisable(true);
            technicTab.setDisable(true);

        } else {
            usersTab.setDisable(!true);
            divisionsTab.setDisable(!true);
            employeesTab.setDisable(!true);
            technicTab.setDisable(!true);
        }
        factory = new SQLDataBaseFactory();

        setChooseComboBoxValues();
        setThemesComboBoxValues();
        setDBSettingsPane();
        referenceDataHash = createHash();
        referenceDBSettingsHash = dbSettingsPaneController.getDataHash();
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
        GUIData.setActiveSQLDataBaseType((String) chooseDBComboBox.getSelectionModel().getSelectedItem());
        setDBSettingsPane();
        calcModifiedDataHash();
        checkHashes();
    }

    @FXML
    void exitButtonClick(ActionEvent event) {
        Stage oldStage = (Stage) exitButton.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    void themeComboBoxClick(ActionEvent event) {
        calcModifiedDataHash();
        checkHashes();
    }

    @FXML
    void applyButtonClick(ActionEvent event) {
        applyChanges();
    }

    void applyChanges() {
        if (isColorThemeToChange()) {
            changeColorTheme();
        }

        if (isDBDataToChange()){
            changeDBSettings();
        }

        referenceDataHash=createHash();
        checkHashes();
    }

    private boolean isColorThemeToChange() {
        return !(themeComboBox.getSelectionModel().getSelectedItem().equals(GUIData.getThemeName()));
    }

    private void changeColorTheme() {
        String newTheme = themeComboBox.getSelectionModel().getSelectedItem().toString();

        GUIData.getIniFile().put("MAIN", "Active theme",newTheme);
        try {
            GUIData.getIniFile().store();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String newPath=ColorTheme.getPathByName(GUIData.getThemes(),newTheme);

        GUIData.setThemeName(newTheme);
        GUIData.setPathCSS(newPath);

        Scene currentScene = exitButton.getScene();
        currentScene.getStylesheets().set(0, GUIData.getPathCSS());

        parentController.setNewTheme();
    }

    private boolean isDBDataToChange() {
        return referenceDBSettingsHash!=dbSettingsPaneController.getDataHash();
    }

    private void changeDBSettings() {
        dbSettingsPaneController.saveInformationToIni();
        GUIData.showAlert("Для применения изменений программа будет перезапущена.");
        exitButton.fire();
        parentController.restartApp();
    }


    private void setThemesComboBoxValues() {
        themesStr = new ArrayList<>();
        for (ColorTheme theme : GUIData.getThemes()) {
            themesStr.add(theme.getName());
        }
        themeComboBox.setItems(FXCollections.observableArrayList(themesStr));
        themeComboBox.getSelectionModel().select(
                themeComboBox.getItems().indexOf(GUIData.getThemeName())
        );
    }

    private void setChooseComboBoxValues() {
        dbTypesStr = new ArrayList<>();

        for (DBType type : GUIData.getDbTypes()) {
            dbTypesStr.add(type.getName());
        }
        chooseDBComboBox.setItems(FXCollections.observableArrayList(dbTypesStr));
        chooseDBComboBox.getSelectionModel().select(
                chooseDBComboBox.getItems().indexOf(GUIData.getActiveSQLDataBaseType())
        );
    }

    private void setDBSettingsPane() {
        if (settingsAnchorPane.getChildren().indexOf(dbSettingsPane) != 0) {
            settingsAnchorPane.getChildren().remove(dbSettingsPane);
        }

        FXMLLoader loader = factory.getPaneByDBType(GUIData.getActiveSQLDataBaseType());
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

    public GUIController getParentController() {
        return parentController;
    }

    public void setParentController(GUIController parentController) {
        this.parentController = parentController;
    }
}
