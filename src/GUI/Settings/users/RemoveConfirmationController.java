package GUI.Settings.users;

import GUI.Settings.SettingsController;
import GUI.Settings.SettingsPaneController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.BL.AppData;
import objects.BL.Users.User;

public class RemoveConfirmationController {
    @FXML
    private Button removeButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField textField;
    @FXML
    private Label recordLabel;
    @FXML
    AnchorPane mainPane;
    private UsersSettingsController parentController;

    private User user;

    @FXML
    public void initialize() {
        user = UsersSettingsController.getUserToDelete();
        recordLabel.setText("Удаляется запись [" + user.getName() + "]");
        applyCSS();
    }

    private void applyCSS() {
        mainPane.getStyleClass().add(0, "anchor-pane-in-tab");
    }

    @FXML
    void cancelButtonClick(ActionEvent event) {
        Stage oldStage = (Stage) cancelButton.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    void removeButtonClick(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Внимание!");
        alert.setHeaderText(null);
        alert.setContentText("Запись: ID" + user.getID() + ": " + user.getName() + " {" + user.getRole() + "} удалена.");
        alert.showAndWait();
        AppData.getUsers().remove(user);
        AppData.getDb().markRecordAsDeleted("users", "user_isdeleted", "user_id", user.getID());
        parentController.fillUsersTableView();
        cancelButton.fire();
    }

    @FXML
    void textFieldChange(Event event) {
        if (textField.getText().equals(user.getName())) {
            removeButton.setDisable(false);
        } else {
            removeButton.setDisable(true);
        }
    }

    protected void setParentController(UsersSettingsController parentController) {
        this.parentController = parentController;
    }
}
