package forms.Settings.users;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    public void initialize(){

    }

    public void cancelButtonClick(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void removeButtonClick(ActionEvent event){
       /* Users selectedRecord = UsersController.getSelectedRecord();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText("The record: ID"+selectedRecord.getID()+": "+selectedRecord.getName()+" {"+selectedRecord.getRole()+"} was removed.");
        alert.showAndWait();

        UsersController.getUsers().remove(selectedRecord);
        UsersController.dbUsers.remove(selectedRecord);
        cancelButtonClick(event);*/
    }

    public void textFiledChange(Event event){
        if (textField.getText().equals("remove")) {
            removeButton.setDisable(false);
        } else {
            removeButton.setDisable(true);
        }
    }

}
