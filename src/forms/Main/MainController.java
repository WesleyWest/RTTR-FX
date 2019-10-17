package forms.Main;

import conf.AppData;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import objects.Employees.Position;
import objects.Request;

import java.util.ArrayList;
import java.util.Date;

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
    private TableView<Request> mainTableView;

    @FXML
    private TableColumn<Request, Integer> idTableColumn;

    @FXML
    private TableColumn<Request, String> technicTableColumn;

    @FXML
    private TableColumn<Request, Date> dateTableColumn;

    @FXML
    private TableColumn<Request, String> descriptionTableColumn;

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
    private Pane closedRequestsFieldsPane;

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
        closedRequestsFieldsPane.setVisible(false);
        informLabel.setText("["+getUser().getUserRole()+"] "+getUser().getUserName());
        setPositions(getDb().readPositionsFromDB());
        setDivisions(getDb().readDivisionsFromDB());
        setEmployees(getDb().readEmployeesFromDB());
        setTypes(getDb().readSimpleDataFromDB("technic_types","Technic types"));
        setStatuses(getDb().readSimpleDataFromDB("technic_statuses","Technic statuses"));


    }

    @FXML
    void ClosedRequestsRadioButtonClick(ActionEvent event) {
        closedRequestsFieldsPane.setVisible(true);
                mainTableView.getStyleClass().set(1,"table-view-closed");
    }

    @FXML
    void ActiveRequestsRadioButtonClick(ActionEvent event) {
        closedRequestsFieldsPane.setVisible(false);
        mainTableView.getStyleClass().set(1,"table-view-active");
    }

    @FXML
    void AddRequestButtonClick(ActionEvent event){

    }

    @FXML
    void exitFromApp(ActionEvent event){
        System.exit(0);
    }
}