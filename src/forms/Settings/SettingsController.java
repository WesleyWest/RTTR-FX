package forms.Settings;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.DB.SQLDataBaseFactory;

public class SettingsController {

    @FXML
    private ComboBox chooseDBComboBox;

    @FXML
    private TextField mySQLHostTextField;

    @FXML
    private TextField mySQLPortTextField;

    @FXML
    private TextField mySQLSchemaTextField;

    @FXML
    private TextField MySQLLoginTextField;

    @FXML
    private TextField MySQLPasswordTextField;

    @FXML
    private TextField SQLitePathTODBTextField;

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

    ObservableList<String> dbTypes;
    ObservableList<String> interfaceColorThemes;

    @FXML
    void initialize() {
        AnchorPane[] panes = {settingsAnchorPane, usersAnchorPane, divisionsAnchorPane, employeesAnchorPane, technicAnchorPane};
        for (AnchorPane pane : panes) {
            pane.getStyleClass().add(0,"anchor-pane-in-tab");
        }
        dbTypes= SQLDataBaseFactory.getDBTypesList();
        chooseDBComboBox.getItems().addAll(dbTypes);
//        chooseDBComboBox.getItems().
    }

    @FXML
    void exitButtonClick(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage oldStage = (Stage) source.getScene().getWindow();
        oldStage.close();
    }


}