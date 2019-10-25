package forms.Settings;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SettingsController {

    @FXML
    private TextField hostTextField;

    @FXML
    private TextField portTextField1;

    @FXML
    private TextField schemaTextField2;

    @FXML
    private TextField loginTextField21;

    @FXML
    private TextField passwordTextField;

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
    private AnchorPane usersAnchorPane;
    @FXML
    private AnchorPane divisionsAnchorPane;
    @FXML
    private AnchorPane employeesAnchorPane;
    @FXML
    private AnchorPane technicAnchorPane;

    @FXML
    void initialize() {
        AnchorPane[] panes = {settingsAnchorPane, usersAnchorPane, divisionsAnchorPane, employeesAnchorPane, technicAnchorPane};
        for (AnchorPane pane : panes) {
            pane.getStyleClass().add(0,"anchor-pane-in-tab");
        }

    }

    @FXML
    void exitButtonClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }


}