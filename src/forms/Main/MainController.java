package forms.Main;

import conf.AppData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import objects.Request;

import java.sql.Timestamp;
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
    private TableColumn<Request, String> dateTableColumn;

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
        mainTableView.getStyleClass().add("table-view-active");
//        mainTableView.setRowFactory();
        closedRequestsFieldsPane.setVisible(false);
        informLabel.setText("[" + getUser().getUserRole() + "] " + getUser().getUserName());
        idTableColumn.setCellValueFactory(new PropertyValueFactory<Request, Integer>("ID"));
        technicTableColumn.setCellValueFactory(new PropertyValueFactory<Request, String>("technic"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<Request, String>("openDate"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<Request, String>("problemDescription"));

        idTableColumn.getStyleClass().add("CENTER");
        dateTableColumn.getStyleClass().add("center");

        mainTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


        changeTableView(false);
        mainTableView.requestFocus();
    }

    private void changeTableView(boolean isClosed) {
        setRequests(getDb().readRequestsFromDB(isClosed));
        mainTableView.setItems(getRequests());
        mainTableView.getSelectionModel().select(0);
    }


    @FXML
    void ClosedRequestsRadioButtonClick(ActionEvent event) {
        closedRequestsFieldsPane.setVisible(true);
        changeTableView(true);

        mainTableView.getStyleClass().set(1, "table-view-closed");
    }

    @FXML
    void ActiveRequestsRadioButtonClick(ActionEvent event) {
        closedRequestsFieldsPane.setVisible(false);
        changeTableView(false);
        mainTableView.getStyleClass().set(1, "table-view-active");
    }

    @FXML
    void AddRequestButtonClick(ActionEvent event) {

    }

    @FXML
    void exitFromApp(ActionEvent event) {
        getDb().close();
        System.exit(0);
    }
}