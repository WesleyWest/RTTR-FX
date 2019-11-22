package forms.Settings;

import forms.GUIController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.GUI.ColorTheme;
import objects.DB.SQLDataBaseFactory;
import objects.DB.DBType;
import objects.GUI.GUIData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

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
    private AnchorPane dbSettingsAnchorPane;
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
    private TabPane tabPane;
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
    Pane usersSettingsPane = null;
    Pane divisionsSettingsPane = null;
    Pane employeesSettingsPane = null;
    Pane technicSettingsPane = null;
    SettingsPaneController dbSettingsPaneController = null;
    SettingsPaneController usersPaneController = null;
    SettingsPaneController divisionsPaneController = null;
    SettingsPaneController employeesPaneController = null;
    SettingsPaneController technicPaneController = null;
    ArrayList<String> dbTypesStr, themesStr;
    private int referenceDataHash, modifiedDataHash, referenceDBSettingsHash;
    private GUIController parentController;

    @FXML
    void initialize() {
        String caller = GUIData.getSettingsWindowCaller();
        if (caller.equals("settingsButton")) {
            usersTab.setDisable(true);
            divisionsTab.setDisable(true);
            employeesTab.setDisable(true);
            technicTab.setDisable(true);
        } else {
            usersTab.setDisable(false);
            divisionsTab.setDisable(false);
            employeesTab.setDisable(false);
            technicTab.setDisable(false);
        }

        factory = new SQLDataBaseFactory();

        setChooseComboBoxValues();
        setThemesComboBoxValues();
        fillTabs();

        referenceDataHash = createHash();
        referenceDBSettingsHash = dbSettingsPaneController.getDataHash();
        applyCSS();

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
            tabChanged();
        });
        setActivePaneByCaller(caller);
    }

    private void setActivePaneByCaller(String caller) {
        String[] tmpCallers = {"settingsMenuItem", "usersMenuItem", "divisionsMenuItem", "employeesMenuItem", "technicMenuitem"};
        ArrayList<String> callers = new ArrayList<>(Arrays.asList(tmpCallers));
        tabPane.getSelectionModel().select(callers.indexOf(caller));
    }

    private void tabChanged() {
        int tabIndex = tabPane.getSelectionModel().getSelectedIndex();
        if (tabIndex != 0) {
            OKButton.setVisible(false);
            applyButton.setVisible(false);
        } else {
            OKButton.setVisible(true);
            applyButton.setVisible(true);
        }
    }

    private void fillTabs() {
        setDBSettingsPane();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("users/UsersSettings.fxml"));
        usersSettingsPane = putAndGetSettingsPane(loader, usersAnchorPane);
        usersPaneController = getSettingsPaneController(loader);


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
    void okButtonClick(ActionEvent event) {
        applyButton.fire();
        exitButton.fire();
    }

    @FXML
    void exitButtonClick(ActionEvent event) {
        Stage oldStage = (Stage) exitButton.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    void applyButtonClick(ActionEvent event) {
        applyChanges();
    }

    /**
     * Main Settings section
     */
    @FXML
    void chooseDBComboBoxClick(ActionEvent event) {
        GUIData.setActiveSQLDataBaseType((String) chooseDBComboBox.getSelectionModel().getSelectedItem());
        setDBSettingsPane();
        calcModifiedDataHash();
        checkHashes();
    }

    @FXML
    void themeComboBoxClick(ActionEvent event) {
        calcModifiedDataHash();
        checkHashes();
    }


    void applyChanges() {
        if (isColorThemeToChange()) {
            changeColorTheme();
        }

        if (isDBDataToChange()) {
            changeDBSettings();
        }

        referenceDataHash = createHash();
        checkHashes();
    }

    private boolean isColorThemeToChange() {
        return !(themeComboBox.getSelectionModel().getSelectedItem().equals(GUIData.getThemeName()));
    }

    private void changeColorTheme() {
        String newTheme = themeComboBox.getSelectionModel().getSelectedItem().toString();

        GUIData.getIniFile().put("MAIN", "Active theme", newTheme);
        try {
            GUIData.getIniFile().store();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String newPath = ColorTheme.getPathByName(GUIData.getThemes(), newTheme);

        GUIData.setThemeName(newTheme);
        GUIData.setPathCSS(newPath);

        Scene currentScene = exitButton.getScene();
        currentScene.getStylesheets().set(0, GUIData.getPathCSS());

        parentController.setNewTheme();
    }

    private boolean isDBDataToChange() {
        return referenceDBSettingsHash != dbSettingsPaneController.getDataHash();
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
        FXMLLoader loader = factory.getPaneByDBType(GUIData.getActiveSQLDataBaseType());
        dbSettingsPane = putAndGetSettingsPane(loader, dbSettingsAnchorPane);
        dbSettingsPaneController = getSettingsPaneController(loader);

    }

    private Pane putAndGetSettingsPane(FXMLLoader loader, AnchorPane parentAnchorPane) {
        parentAnchorPane.getChildren().clear();
        Pane childPane = null;
        try {
            childPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (childPane != null) {
            parentAnchorPane.getChildren().add(childPane);
//            childPane.setLayoutX(14);
//            childPane.setLayoutY(70);
        }
        return childPane;
    }

    private SettingsPaneController getSettingsPaneController(FXMLLoader loader) {
        SettingsPaneController childController = null;
        childController = loader.getController();
        childController.setParentController(this);
        childController.setInformation();
        return childController;
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
