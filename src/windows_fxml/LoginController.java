package windows_fxml;

import javafx.concurrent.Task;
import javafx.concurrent.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import objects.MySQLDataBase;
import objects.User;

import java.util.ArrayList;

public class LoginController {

    @FXML
    private Button exitButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordField;


    private MySQLDataBase db = null;
    private ArrayList<User> users = null;
    private int step = 0;


    @FXML
    void initialize() {

    }

    @FXML
    void exitButtonClick(ActionEvent event) {
        if (db != null) db.close();
        System.exit(0);
    }


    @FXML
    void loginButtonClick(ActionEvent event) {
        animCheckingUser(true);
        getUsersFromDB();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String userName = loginTextField.getText();
        String userPassword = passwordField.getText();
        alert.setTitle("RTTR-Master");
        alert.setHeaderText(null);
        if (!(userFound(userName, userPassword))) {
            alert.setContentText("Пользователь не найден");
            alert.showAndWait();
            animCheckingUser(false);
        } else {

        }

    }

    private void animCheckingUser(boolean direction) {

    }

    private void getUsersFromDB() {
        db = new MySQLDataBase(this);
        db.open();
        db.setRoleNamesFromDB();
        users = db.readUsersFromDB();
    }

    private boolean userFound(String userName, String userPassword) {
        for (User user : users) {
            if (userName.equals(user.getUserName()) && userPassword.equals(user.getUserPassword())) {
                return true;
            }
        }
        return false;
    }


}
