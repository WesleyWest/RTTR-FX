package forms.Login;

import conf.AppData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.MySQLDataBase;
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
    void initialize() {
        loginTextField.setText("Stas");
        passwordField.setText("123");


    }

    @FXML
    void exitButtonClick(ActionEvent event) {
        if (getDb() != null) getDb().close();
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

            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("../Main/MainWindow.fxml"));
            stage.setTitle("RTTR-Master (Requests to Technics Repair)");
            stage.setMinHeight(768);
            stage.setMinWidth(1024);
            stage.getIcons().add(new Image("resources/main.png"));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node) event.getSource()).getScene().getWindow());
            stage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getUsersFromDB() {
        setDb(new MySQLDataBase());
        getDb().open();
        getDb().setRoleNamesFromDB();
        setUsers(getDb().readUsersFromDB());
    }

    private boolean userFound(String userName, String userPassword) {
        for (User user : getUsers()) {
            if (userName.equals(user.getUserName()) && userPassword.equals(user.getUserPassword())) {
                AppData.setUser(user);
                return true;
            }
        }
        return false;
    }


}
