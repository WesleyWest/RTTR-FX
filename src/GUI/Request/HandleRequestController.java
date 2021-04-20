package GUI.Request;

import GUI.Main.MainController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.BL.AppData;
import objects.BL.Employees.Employee;
import objects.BL.Request;
import objects.BL.Technic.Technic;
import objects.BL.Users.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HandleRequestController {


    @FXML
    private Button exitButton;
    @FXML
    private Button applyButton;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private AnchorPane handleRequestAnchorPane;
    @FXML
    private AnchorPane headerAnchorPane;
    @FXML
    private Label headerLabelBig;
    @FXML
    private Label headerLabelSmall;
    @FXML
    private CheckBox closeRequestCheckBox;
    @FXML
    private TextField idTextField;
    @FXML
    private TextField requestOpenTimeHoursField;
    @FXML
    private TextField requestOpenTimeMinutesField;
    @FXML
    private DatePicker requestOpenDatePicker;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField closerTextField;
    @FXML
    private TextField problemDescriptionTextField;
    @FXML
    private Button setOpenTimeToNowButton;
    @FXML
    private Button setCloseTimeToNowButton;
    @FXML
    private TextField requestCloseTimeHoursField;
    @FXML
    private TextField requestCloseTimeMinutesField;
    @FXML
    private DatePicker requestCloseDatePicker;

    private TextField worksDescriptionTextField;
    @FXML
    private ComboBox<Technic> technicComboBox;
    @FXML
    private ComboBox<Employee> ownerComboBox;
    @FXML
    private ComboBox<User> repairerComboBox;

    private MainController parentController;
    private static boolean isEditingMode = false;
    private Request activeRequest;
    private int referenceDataHash;

    public void setParentController(MainController parentController) {
        this.parentController = parentController;
    }

    public MainController getParentController() {
        return parentController;
    }

    private ArrayList<Technic> allTechnic = new ArrayList<>();

    int calculateCurrentDataHash() {

        int tmpHash = 0;
        try {
            tmpHash = requestOpenDatePicker.getValue().hashCode()
                    + requestOpenTimeHoursField.getText().hashCode()
                    + requestOpenTimeMinutesField.getText().hashCode()
                    + ownerComboBox.getSelectionModel().getSelectedItem().toString().hashCode()
                    + technicComboBox.getSelectionModel().getSelectedItem().toString().hashCode()
                    + repairerComboBox.getSelectionModel().getSelectedItem().toString().hashCode()
                    + problemDescriptionTextField.getText().hashCode();

            if (handleRequestAnchorPane.isVisible()) {
                tmpHash = tmpHash
                        + closerTextField.getText().hashCode()
                        + requestCloseDatePicker.getValue().hashCode()
                        + requestCloseTimeHoursField.getText().hashCode()
                        + requestCloseTimeMinutesField.getText().hashCode()
                        + worksDescriptionTextField.getText().hashCode();
            }
        } catch (Exception e) {

        }
        return tmpHash;
    }

    @FXML
    void checkChanges() {
        if (referenceDataHash != calculateCurrentDataHash()) {
            applyButton.setDisable(false);
        } else {
            applyButton.setDisable(true);
        }
    }

    @FXML
    void initialize() {
        applyCSS();
        handleRequestAnchorPane.setVisible(false);
    }

    void applyCSS() {
        headerLabelBig.getStyleClass().set(0, "label-header-big");
        headerLabelSmall.getStyleClass().set(0, "label-header-small");
        headerAnchorPane.getStyleClass().add("anchor-pane-header");
        mainAnchorPane.getStyleClass().add("anchor-pane-main");
    }

    public void setData(boolean isEditing) {
        isEditingMode = isEditing;
        setApplyButtonByMode();

        EventHandler<ActionEvent> ownerHandler = ownerComboBox.getOnAction();
        EventHandler<ActionEvent> repairerHandler = repairerComboBox.getOnAction();
        EventHandler<ActionEvent> technicHandler = technicComboBox.getOnAction();

        ownerComboBox.setOnAction(null);
        repairerComboBox.setOnAction(null);
        technicComboBox.setOnAction(null);

        fillFieldsByMode();
        filterTechnicByOwner();
        ownerComboBox.setOnAction(ownerHandler);
        repairerComboBox.setOnAction(repairerHandler);
        technicComboBox.setOnAction(technicHandler);

        referenceDataHash = calculateCurrentDataHash();
        checkChanges();
    }

    private void setApplyButtonByMode() {
        if (isEditingMode) {
            applyButton.setText("Применить");
        } else {
            applyButton.setText("Добавить");
        }
    }

    private void fillFieldsByMode() {
        if (isEditingMode) {
            activeRequest = parentController.getActiveRequest();
            fillComboBoxes();
            idTextField.setText(activeRequest.getID().toString());
            authorTextField.setText(activeRequest.getAuthor().getEmployee().getShortDescription());

            LocalDateTime ldt = activeRequest.getOpenDateTime().toLocalDateTime();
            requestOpenDatePicker.setValue(ldt.toLocalDate());
            requestOpenTimeHoursField.setText(String.valueOf(ldt.getHour()));
            requestOpenTimeMinutesField.setText(String.valueOf(ldt.getMinute()));

            ownerComboBox.getSelectionModel().select(activeRequest.getTechnicAsObject().getOwner());
            repairerComboBox.getSelectionModel().select(activeRequest.getRepairer());
            problemDescriptionTextField.setText(activeRequest.getProblemDescription());
        } else {

        }

    }

    private void fillComboBoxes() {
        ownerComboBox.getItems().clear();
        ownerComboBox.setItems(Employee.sortEmployeeList(AppData.getListOfObjects(AppData.getEmployeesWithoutEmptyObject(), false)));
        repairerComboBox.getItems().clear();
        repairerComboBox.setItems(AppData.getListOfObjects(AppData.getUsersWithoutBuiltedIn(), false));
        allTechnic.clear();
        for (Technic technic : AppData.getListOfObjects(AppData.getTechnic(), false)) {
            allTechnic.add(technic);
        }

    }

    @FXML
    void filterTechnicByOwner() {
        ArrayList<Technic> res = new ArrayList();
        Employee activeOwner = ownerComboBox.getSelectionModel().getSelectedItem();
        for (Technic technic : allTechnic) {
            if (technic.getOwner().equals(activeOwner)) res.add(technic);
        }

        EventHandler<ActionEvent> technicHandler = technicComboBox.getOnAction();
        technicComboBox.setOnAction(null);
        technicComboBox.getItems().clear();
        technicComboBox.setItems(FXCollections.observableArrayList(res));
        if (activeOwner != activeRequest.getTechnicAsObject().getOwner()) {
            technicComboBox.getSelectionModel().select(0);
        } else {
            technicComboBox.getSelectionModel().select(activeRequest.getTechnicAsObject());
        }
        technicComboBox.setOnAction(technicHandler);
        checkChanges();
    }

    @FXML
    void setTimeToNow(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        Button clickedButton = (Button) event.getSource();
        if (clickedButton.getId().equals("setOpenTimeToNowButton")) {
            requestOpenDatePicker.setValue(now.toLocalDate());
            requestOpenTimeHoursField.setText(String.valueOf(now.getHour()));
            requestOpenTimeMinutesField.setText(String.valueOf(now.getMinute()));
        } else {
            requestCloseDatePicker.setValue(now.toLocalDate());
            requestCloseTimeHoursField.setText(String.valueOf(now.getHour()));
            requestCloseTimeMinutesField.setText(String.valueOf(now.getMinute()));
        }
        checkChanges();
    }

    @FXML
    void closeRequestCheckBoxFire() {
        if (closeRequestCheckBox.isSelected()) {
            applyButton.setText("Завершить");
            handleRequestAnchorPane.setDisable(false);
            handleRequestAnchorPane.setVisible(true);
        } else {
            setApplyButtonByMode();
            handleRequestAnchorPane.setDisable(true);
            handleRequestAnchorPane.setVisible(false);
        }

    }

    @FXML
    void exitButtonClick(ActionEvent event) {
        Stage oldStage = (Stage) exitButton.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    void applyButtonClick(ActionEvent event) {
        if (isEditingMode) {

        } else {

        }
    }

}
