package forms.Main;

import forms.GUIController;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import objects.BL.Employees.Employee;
import objects.BL.Request;
import objects.BL.Technic.Technic;
import objects.GUI.GUIData;
import org.controlsfx.control.PopOver;

public class MainController extends GUIController {

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
    private ToggleButton closedRequestsToggleButton;

    @FXML
    private ToggleButton activeRequestsToggleButtton;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField technicField;

    @FXML
    private TextField requestOpenTimeField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField ownerTextField;

    @FXML
    private TextField repairerTextField;

    @FXML
    private TextField problemDescriptionTextField;

    @FXML
    private TextField requestCloseTimeField;

    @FXML
    private TextField worksDescriptionTextField;

    @FXML
    private TextField closerTextField;

    @FXML
    private Button addRequestButton;

    @FXML
    private Button editrequestButton;

    @FXML
    private Button closeRequestButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button userButton;

    @FXML
    private Label informLabel;

    @FXML
    private AnchorPane closedRequestsAnchorPane;

    @FXML
    private AnchorPane mainAnchorPane;

    @FXML
    private AnchorPane headerAnchorPane;

    @FXML
    private Label headerLabelBig;

    @FXML
    private Label headerLabelSmall;

    private PopOver popOver;

    private Request selectedRecord;

    @FXML
    void initialize() {
        initListeners();

        Employee emp = getObjectByID(getEmployees(), getUser().getEmployee().getID());
        Label lbl1 = new Label("\n   Логин: " + getUser().getName());
        Label lbl2 = new Label("\n   Роль : " + getUser().getRole());
        Label lbl3 = new Label("\n   Ф.И.О : " + emp.getLastName() + " " + emp.getName() + " " + emp.getMiddleName() + "   ");
        Label lbl4 = new Label("\n   Звание: " + emp.getPosition().getDescription() + "        ");
        Label lbl5 = new Label("\n   Подразделение: " + emp.getDivision().getDescription() + "\n      ");
        VBox vBox = new VBox(lbl1, lbl2, lbl3, lbl4, lbl5);

        popOver = new PopOver(vBox);
        popOver.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);

        closedRequestsAnchorPane.setVisible(false);

        applyCSS();

        idTableColumn.setCellValueFactory(new PropertyValueFactory<Request, Integer>("ID"));
        technicTableColumn.setCellValueFactory(new PropertyValueFactory<Request, String>("technic"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<Request, String>("openDate"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<Request, String>("problemDescription"));

        mainTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        changeTableView(false);

        informLabel.setText("Количество открытых заявок: " + getRequests().size());
    }

    private void initListeners() {
        mainTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                mainTableViewKeyReleased(event);
            }
//                if (event.getClickCount() == 2) {
//                    mainEditButton.fire();
//                }

        });
    }

    private void applyCSS() {
        informLabel.getStyleClass().set(0, "inform-label");
        headerLabelBig.getStyleClass().set(0, "label-header-big");
        headerLabelSmall.getStyleClass().set(0, "label-header-small");

        headerAnchorPane.getStyleClass().add("anchor-pane-header");
        mainAnchorPane.getStyleClass().add("anchor-pane-main");

        mainTableView.getStyleClass().add("table-view-active");

        activeRequestsToggleButtton.getStyleClass().add("toggle-button-active");
        closedRequestsToggleButton.getStyleClass().add("toggle-button-closed");
    }

    private void changeTableView(boolean isClosed) {
        setRequests(getDb().readRequestsFromDB(isClosed));
        mainTableView.setItems(getRequests());
        mainTableView.getSelectionModel().select(0);
        setFieldsValues();
        mainTableView.requestFocus();
    }

    private void setFieldsValues() {

        selectedRecord = mainTableView.getSelectionModel().getSelectedItem();
        idTextField.setText(selectedRecord.getID().toString());

        Employee author = getObjectByID(getEmployees(), selectedRecord.getAuthor().getID());
        authorTextField.setText(Employee.getFullEmployeeDescription(author));

        Technic technic = selectedRecord.getTechnicAsObject();
        technicField.setText(technic.getType().getDescription() + " " + technic.getName());

        requestOpenTimeField.setText(selectedRecord.getOpenDate());

        Employee owner = selectedRecord.getTechnicAsObject().getOwner();
        ownerTextField.setText(Employee.getFullEmployeeDescription(owner));

        Employee repairer = selectedRecord.getTechnicAsObject().getRepairer();
        repairerTextField.setText(Employee.getFullEmployeeDescription(repairer));

        problemDescriptionTextField.setText(selectedRecord.getProblemDescription());

        if (selectedRecord.getStatus()) {
            Employee closer = getObjectByID(getEmployees(), selectedRecord.getCloser().getID());
            closerTextField.setText(Employee.getFullEmployeeDescription(closer));
            requestCloseTimeField.setText(selectedRecord.getCloseDate());
            worksDescriptionTextField.setText(selectedRecord.getDecisionDescription());
        }
    }

    @FXML
    void closedRequestsToggleButtonClick(ActionEvent event) {
        if (!closedRequestsToggleButton.isSelected()) {
            closedRequestsToggleButton.setSelected(true);
            return;
        }
        closedRequestsAnchorPane.setVisible(true);
        mainTableView.getStyleClass().set(1, "table-view-closed");
        changeTableView(true);
        informLabel.setText("Количество закрытых заявок: " + getRequests().size());
    }

    @FXML
    void activeRequestsToggleButtonClick(ActionEvent event) {
        if (!activeRequestsToggleButtton.isSelected()) {
            activeRequestsToggleButtton.setSelected(true);
            return;
        }
        closedRequestsAnchorPane.setVisible(false);
        mainTableView.getStyleClass().set(1, "table-view-active");
        changeTableView(false);
        informLabel.setText("Количество открытых заявок: " + getRequests().size());
    }

    @FXML
    void AddRequestButtonClick(ActionEvent event) {

    }

    @FXML
    void exitFromApp(ActionEvent event) {
        getDb().close();
        System.exit(0);
    }

    @FXML
    void mainTableViewKeyReleased(Event event) {
        setFieldsValues();
    }

    @FXML
    void userButtonClick(ActionEvent event) {
        if (!popOver.isShowing()) {
            popOver.show(userButton);
        } else {
            popOver.hide();
        }
    }

    @FXML
    void settingMenuItemClick(ActionEvent event) {
        callSettingsWindow(event);
    }

    private void callSettingsWindow(ActionEvent event) {
        try {
            MenuItem mItem = (MenuItem) event.getSource();
            GUIData.setSettingsWindowCaller(mItem.getId());

            Parent root = initializeNewSettingsWindow();
            GUIData.openCustomWindow(event, root, 840, 610, Modality.APPLICATION_MODAL, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setNewTheme() {
        Scene currentScene = exitButton.getScene();
        currentScene.getStylesheets().set(0, GUIData.getPathCSS());
    }

    public void restartApp() {
        restart((Stage) exitButton.getScene().getWindow());
    }

}