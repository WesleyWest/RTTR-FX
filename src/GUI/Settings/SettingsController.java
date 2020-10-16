package GUI.Settings;

import GUI.GUIController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.DB.DBType;
import objects.DB.SQLDataBaseFactory;
import objects.GUI.ColorTheme;
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
    private AnchorPane positionsAnchorPane;
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
    private Tab positionsTab;
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
    Pane positionsSettingsPane = null;
    Pane employeesSettingsPane = null;
    Pane technicSettingsPane = null;
    SettingsPaneController dbSettingsPaneController = null;
    SettingsPaneController usersPaneController = null;
    SettingsPaneController divisionsPaneController = null;
    SettingsPaneController positionPaneController = null;
    SettingsPaneController employeesPaneController = null;
    SettingsPaneController technicPaneController = null;
    ArrayList<String> dbTypesStr, themesStr;
    private int referenceDataHash, modifiedDataHash, referenceDBSettingsHash;
    private GUIController parentController;
    private String caller;
    private ArrayList<Tab> tabs;

    @FXML
    void initialize() {
        Tab[] tmpTabs = {settingsTab, usersTab, divisionsTab, positionsTab, employeesTab, technicTab};
        tabs = new ArrayList<>(Arrays.asList(tmpTabs));

        caller = GUIData.getSettingsWindowCaller();
        if (caller.equals("settingsButton")) {
            setTabsDisabled("100000");
        } else {
            setTabsDisabled("111111");
        }

        factory = new SQLDataBaseFactory();

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, ov, nv) -> {
            tabChanged();
        });

        fillTabs();
        setActivePaneByCaller(caller);
        setChooseComboBoxValues();
        setThemesComboBoxValues();

        referenceDataHash = createHash();
        referenceDBSettingsHash = dbSettingsPaneController.getDataHash();
        applyCSS();
    }

    public void setTabsDisabled(String keyString) {
        for (int i = 0; i < tabs.size(); i++) {
            if (keyString.charAt(i) == '1') {
                tabs.get(i).setDisable(false);
            } else {
                tabs.get(i).setDisable(true);
            }
        }
    }

    private void setActivePaneByCaller(String caller) {
        String[] tmpCallers = { "settingsMenuItem",
                                "usersMenuItem",
                                "divisionsMenuItem",
                                "positionsMenuItem",
                                "employeesMenuItem",
                                "technicMenuitem"};
        ArrayList<String> callers = new ArrayList<>(Arrays.asList(tmpCallers));
        tabPane.getSelectionModel().select(callers.indexOf(caller));
        tabChanged();
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

        if (tabIndex == 1) {
            usersPaneController.setInformation();
        }
        if (tabIndex == 2) {
            divisionsPaneController.setInformation();
        }
        if (tabIndex == 3) {
            positionPaneController.setInformation();
        }
        if (tabIndex == 4) {
            employeesPaneController.setInformation();
        }
        if (tabIndex == 5) {
            technicPaneController.setInformation();
        }
    }

    private void fillTabs() {
        setDBSettingsPane();
        if (!caller.equals("settingsButton")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("users/UsersSettings.fxml"));
            usersSettingsPane = putAndGetSettingsPane(loader, usersAnchorPane);
            usersPaneController = getSettingsPaneController(loader);

            loader = new FXMLLoader(getClass().getResource("divisions/DivisionsSettings.fxml"));
            divisionsSettingsPane = putAndGetSettingsPane(loader, divisionsAnchorPane);
            divisionsPaneController = getSettingsPaneController(loader);

            loader = new FXMLLoader(getClass().getResource("positions/PositionsSettings.fxml"));
            positionsSettingsPane = putAndGetSettingsPane(loader, positionsAnchorPane);
            positionPaneController = getSettingsPaneController(loader);

            loader = new FXMLLoader(getClass().getResource("employees/EmployeesSettings.fxml"));
            employeesSettingsPane = putAndGetSettingsPane(loader, employeesAnchorPane);
            employeesPaneController = getSettingsPaneController(loader);

            loader = new FXMLLoader(getClass().getResource("technic/TechnicSettings.fxml"));
            technicSettingsPane = putAndGetSettingsPane(loader, technicAnchorPane);
            technicPaneController = getSettingsPaneController(loader);
        }
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

    void applyCSS() {
        AnchorPane[] panes = {settingsAnchorPane,
                usersAnchorPane,
                divisionsAnchorPane,
                positionsAnchorPane,
                employeesAnchorPane,
                technicAnchorPane};
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


    int createHash() {
        return themeComboBox.getSelectionModel().getSelectedItem().hashCode() +
                chooseDBComboBox.getSelectionModel().getSelectedItem().hashCode() +
                dbSettingsPaneController.getDataHash();
    }

    public void setExitButtonVisible(boolean state) {
        exitButton.setVisible(state);
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
