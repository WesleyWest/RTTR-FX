package forms.Login;

import forms.GUIController;
import forms.Settings.SettingsController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.BL.AppData;
import objects.DB.SQLDataBaseFactory;
import objects.BL.Users.User;
import objects.GUI.GUIData;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController extends GUIController {

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button settingsButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private AnchorPane headerAnchorPane;

    @FXML
    private Label headerLabelBig;

    @FXML
    private Label headerLabelSmall;

    private SettingsController settingsController;

    @FXML
    void initialize() {
        loginTextField.setText("Wesley");
        passwordField.setText("123");

        applyCSS();
    }

    private void applyCSS() {
        mainAnchorPane.getStyleClass().add("anchor-pane-main");
        headerAnchorPane.getStyleClass().add("anchor-pane-header");
        headerLabelBig.getStyleClass().set(0, "label-header-big");
        headerLabelSmall.getStyleClass().set(0, "label-header-small");
        settingsButton.getStyleClass().set(0, "settings-button");
    }

    @FXML
    void settingsButtonClick(ActionEvent event) {
        callSettingsWindow(event);
    }

    private void callSettingsWindow(ActionEvent event) {
        try {
            Button button = (Button) event.getSource();
            GUIData.setSettingsWindowCaller(button.getId());

            Parent root = initializeNewSettingsWindow();
            GUIData.openCustomWindow(event, root, 840, 610, Modality.APPLICATION_MODAL, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void exitButtonClick(ActionEvent event) {
        if (getDb() != null)
            if (getDb().isOpened()) getDb().close();
        System.exit(0);
    }


    @FXML
    void loginButtonClick(ActionEvent event) {

        if (getUsers() == null) getUsersFromDB();

        String userName = loginTextField.getText();
        String userPassword = passwordField.getText();

        if (userName.length() == 0 || userPassword.length() == 0) {
            GUIData.showAlert("Заполните все поля.");
        } else if (!(userFound(userName, userPassword))) {
            GUIData.showAlert("Пользователь не найден.");
        } else if (!getUser().isActive()) {
            GUIData.showAlert("Учётная запись не активна.");
        } else {
            setDivisions(getDb().readDivisionsFromDB());
            setPositions(getDb().readSimpleObjectsListFromDB("employee_positions", "Employees positions"));
            setEmployees(getDb().readEmployeesFromDB());

            setTypes(getDb().readSimpleObjectsListFromDB("technic_types", "Technic types"));
            setStatuses(getDb().readSimpleObjectsListFromDB("technic_statuses", "Technic statuses"));
            setTechnic(getDb().readTechnicFromDB());
            openMainWindow(event);
        }
    }

    private void openMainWindow(ActionEvent event) {
        try {
            Node source = (Node) event.getSource();
            Stage oldStage = (Stage) source.getScene().getWindow();
            oldStage.close();

            Parent root = FXMLLoader.load(getClass().getResource("../Main/MainWindow.fxml"));
            GUIData.setOwner(((Node) event.getSource()).getScene().getWindow());
            GUIData.openCustomWindow(event, root, 1044, 768, Modality.WINDOW_MODAL, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUsersFromDB() {
        try {
            setDb(new SQLDataBaseFactory().getSQLDataBaseByType(GUIData.getActiveSQLDataBaseType()));
            getDb().open();
            getDb().setRoleNamesFromDB();
            setUsers(getDb().readUsersFromDB());
        } catch (ClassNotFoundException e) {
            GUIData.showAlert("Class not found: " + e.getLocalizedMessage());
        } catch (InstantiationException e) {
            GUIData.showAlert("Can't instant object: " + e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            GUIData.showAlert("Illegal access exception: " + e.getLocalizedMessage());
        } catch (SQLException e) {
            GUIData.showAlert("Файл не найден: \n" + e.getLocalizedMessage());
        } catch (Exception e) {
            GUIData.showAlert("Что-то ещё не так: \n" + e.getLocalizedMessage());
        }
    }

    private boolean userFound(String userName, String userPassword) {
        for (User user : getUsers()) {
            if (userName.toLowerCase().equals(user.getUserName().toLowerCase())
                    && userPassword.equals(user.getUserPassword())) {
                AppData.setUser(user);
                return true;
            }
        }
        return false;
    }


    @Override
    public void setNewTheme() {
        Scene currentScene = exitButton.getScene();
        currentScene.getStylesheets().set(0, GUIData.getPathCSS());
    }

    @Override
    public void restartApp() {
        restart((Stage) exitButton.getScene().getWindow());
    }
}
