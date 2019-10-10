package forms.Main;

import conf.AppData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

public class MainController extends AppData {

    @FXML
    private MenuItem createReportMenuItem;

    @FXML
    private MenuItem exitMenuItem;

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
    private RadioButton closedRequestsRadioButton;

    @FXML
    private RadioButton activeRequestsRadioButtton;

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
    private Label informLabel;

    @FXML
    void initialize() {
        closedReqestsFieldsPane.setVisible(false);
        informLabel.setText("["+getUser().getUserRole()+"] "+getUser().getUserName());

    }

    @FXML
    void ClosedRequestsRadioButtonClick(ActionEvent event) {
        closedReqestsFieldsPane.setVisible(true);

    }

    @FXML
    void ActiveRequestsRadioButtton(ActionEvent event) {
        closedReqestsFieldsPane.setVisible(false);

    }

    @FXML
    void exitFromApp(ActionEvent event){
        System.exit(0);
    }
}