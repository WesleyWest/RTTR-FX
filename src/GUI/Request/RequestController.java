package GUI.Request;

import GUI.Main.MainController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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

public class RequestController {


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
    private TextField requestOpenTimeField;
    @FXML
    private DatePicker requestOpenDatePicker;
    @FXML
    private TextField authorTextField;
    @FXML
    private TextField problemDescriptionTextField;
    @FXML
    private TextField requestCloseTimeField;
    @FXML
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


    public void setParentController(MainController parentController) {
        this.parentController = parentController;
    }

    public MainController getParentController() {
        return parentController;
    }

    private ArrayList<Technic> allTechnic = new ArrayList<>();

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


    private void fillFieldsByMode() {
        fillComboBoxes();
        if (isEditingMode) {
            activeRequest = parentController.getActiveRequest();
            idTextField.setText(activeRequest.getID().toString());
            authorTextField.setText(activeRequest.getAuthor().getEmployee().getShortDescription());

            LocalDateTime ldt = activeRequest.getOpenDate().toLocalDateTime();
            requestOpenDatePicker.setValue(ldt.toLocalDate());
            String requestTime = String.valueOf(ldt.getHour());
            requestTime = requestTime +":"+ldt.getMinute();
            requestOpenTimeField.setText(requestTime);

            ownerComboBox.getSelectionModel().select(activeRequest.getTechnicAsObject().getOwner());
            repairerComboBox.getSelectionModel().select(activeRequest.getRepairer());
            problemDescriptionTextField.setText(activeRequest.getProblemDescription());
        } else {

        }

    }

    private void fillComboBoxes() {
        allTechnic.clear();
        for (Technic technic : AppData.getListOfObjects(AppData.getTechnic(), false)) {
            allTechnic.add(technic);
        }
        technicComboBox.getItems().clear();

        ownerComboBox.getItems().clear();
        ownerComboBox.setItems(Employee.sortEmployeeList(AppData.getListOfObjects(AppData.getEmployeesWithoutEmptyObject(), false)));

        repairerComboBox.getItems().clear();
        repairerComboBox.setItems(AppData.getListOfObjects(AppData.getUsersWithoutBuiltedIn(), false));
    }

    @FXML
    void filterTechnicByOwner() {
        ArrayList<Technic> res = new ArrayList();
        Employee activeOwner = ownerComboBox.getSelectionModel().getSelectedItem();
        for (Technic technic : allTechnic) {
            if (technic.getOwner() == activeOwner) res.add(technic);
        }
        technicComboBox.getItems().clear();
        technicComboBox.setItems(FXCollections.observableArrayList(res));
        if (activeOwner != activeRequest.getTechnicAsObject().getOwner()) {
            technicComboBox.getSelectionModel().select(0);
        } else {
            technicComboBox.getSelectionModel().select(activeRequest.getTechnicAsObject());
        }
    }

    private void setApplyButtonByMode() {
        if (isEditingMode) {
            applyButton.setText("Применить");
        } else {
            applyButton.setText("Добавить");
        }
    }

    @FXML
    void closeRequestCheckBoxFire() {
        if (closeRequestCheckBox.isSelected()) {
            applyButton.setText("Завершить");
            handleRequestAnchorPane.setVisible(true);
        } else {
            setApplyButtonByMode();
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

    public void setData(boolean isEditing) {
        isEditingMode = isEditing;
        setApplyButtonByMode();
        fillFieldsByMode();

    }


/**
 * Main Settings section
 */

    /*public GUIController getParentController() {
        return parentController;
    }

    public void setParentController(GUIController parentController) {
        this.parentController = parentController;
    }*/
}
