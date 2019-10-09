package Windows.Login;

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


        String userName = loginTextField.getText();
        String userPassword = passwordField.getText();

        if(userName.length()==0 || userPassword.length()==0){
            showAlert("Заполните все поля.");
        }
        if (!(userFound(userName, userPassword))) {
            showAlert("Пользователь не найден.");
            animCheckingUser(false);
        } else {

        }

    }

    private void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("RTTR-Master");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
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
