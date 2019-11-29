package forms.Settings.users;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

    private User user;

    @FXML
    public void initialize(){
        user = UsersSettingsController.getUserToDelete();
        recordLabel.setText("Удаляется запись ["+user.getName()+"]");
    }

    public void cancelButtonClick(ActionEvent event){
        Stage oldStage = (Stage) cancelButton.getScene().getWindow();
        oldStage.close();
    }

    public void removeButtonClick(ActionEvent event){

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Внимание!");
        alert.setHeaderText(null);
        alert.setContentText("Запись: ID"+user.getID()+": "+user.getName()+" {"+user.getRole()+"} удалена.");
        alert.showAndWait();
        AppData.getUsers().remove(user);
        AppData.getDb().markRecordAsDeleted("users","user_isdeleted","user_id",user.getID());
        cancelButtonClick(event);
    }

    public void textFieldChange(Event event){
        if (textField.getText().equals(user.getName())) {
            removeButton.setDisable(false);
        } else {
            removeButton.setDisable(true);
        }
    }
}
