package Windows.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class MainController {

    @FXML
    private MenuItem createReportMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem addRequestMenuItem;

    @FXML
    private MenuItem editRequestMenuItem;

    @FXML
    private MenuItem closeRequestMenuItem;

    @FXML
    private MenuItem settingsMenuItem;

    @FXML
    private MenuItem usersMenuItem;

    @FXML
    private MenuItem divisionsMenuItem;

    @FXML
    private MenuItem employeesMenuItem;

    @FXML
    private MenuItem technicMenuitem;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private TableView<?> mainTableView;

    @FXML
    private TableColumn<?, ?> idTableColumn;

    @FXML
    private TableColumn<?, ?> technicTableColumn;

    @FXML
    private TableColumn<?, ?> dateTableColumn;

    @FXML
    private TableColumn<?, ?> descriptionTableColumn;

    @FXML
    private ToggleGroup reqGroup;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField techinc;

    @FXML
    private TextField dateTimeTextField;

    @FXML
    private TextField ownerTextField;

    @FXML
    private TextField repairerTextField;

    @FXML
    private TextArea problemDescriptionTextArea;

    @FXML
    private Pane closedReqestsFieldsPane;

    @FXML
    private TextField dateTimeClosedRequestTextField;

    @FXML
    private TextArea worksDescriptionTextField;

    @FXML
    private Button addRequestButton;

    @FXML
    private Button editrequestButton;

    @FXML
    private Button closeRequestButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label infomLabel;

    @FXML
    void activeRequestsRadioButtton(ActionEvent event) {

    }

    @FXML
    void closedRequestsRadioButton(ActionEvent event) {

    }

}