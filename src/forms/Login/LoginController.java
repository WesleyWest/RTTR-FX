package forms.Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.AppData;
import objects.DB.SQLDataBaseFactory;
import objects.Users.User;

import java.io.IOException;

public class LoginController extends AppData {

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private AnchorPane mainAnchorPane;


    @FXML
    void initialize() {
        loginTextField.setText("Wesley");
        passwordField.setText("123");

        mainAnchorPane.getStyleClass().add("anchor-pane-main");
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
            showAlert("Заполните все поля.");
        } else if (!(userFound(userName, userPassword))) {
            showAlert("Пользователь не найден.");
        } else if (!getUser().isActive()) {
            showAlert("Учётная запись не активна.");
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
            AppData.setOwner(((Node) event.getSource()).getScene().getWindow());
            AppData.openCustomWindow(event, root, 1044, 768, Modality.WINDOW_MODAL, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUsersFromDB() {
        setDb(new SQLDataBaseFactory().getSQLDataBaseByType(getActiveSQLDataBaseType()));
        getDb().open();
        getDb().setRoleNamesFromDB();
        setUsers(getDb().readUsersFromDB());
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


}
