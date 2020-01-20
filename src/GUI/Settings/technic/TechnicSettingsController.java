package GUI.Settings.technic;

import GUI.Settings.SettingsPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import objects.BL.AppData;
import objects.BL.Employees.Employee;
import objects.BL.Technic.Technic;
import objects.BL.Technic.TechnicStatus;
import objects.BL.Technic.TechnicType;

public class TechnicSettingsController extends SettingsPaneController {

    @FXML
    private Label countLabel;

    @FXML
    private TableView<Technic> technicTableView;

    @FXML
    private TableColumn<Technic, String> nameColumn;

    @FXML
    private TableColumn<Technic, String> typeColumn;

    @FXML
    private TableColumn<Technic, String> statusColumn;

    @FXML
    private TableColumn<Technic, String> ownerColumn;

    @FXML
    private ButtonBar mainButtonBar;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private ButtonBar secondButtonBar;

    @FXML
    private Button applyButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;


    @FXML
    private Label modeLabel;

    @FXML
    private ComboBox<TechnicType> typeComboBox;

    @FXML
    private ComboBox<TechnicStatus> statusComboBox;

    @FXML
    private ComboBox<Employee> ownerComboBox;

    @FXML
    private TextField idTextField;

    @FXML
    private Button editTypeListButton;

    @FXML
    private Button editStatusListButton;

    private static Technic selectedRecord;
    private int indexOfSelectedRecord;

    @FXML
    void initialize(){
        initListeners();
        applyCSS();
        nameColumn.setCellValueFactory(new PropertyValueFactory<Technic, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Technic, String>("stringType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Technic, String>("stringStatus"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<Technic, String>("stringOwner"));
        secondButtonBar.setVisible(false);
    }

    private void initListeners() {


    }

    private void applyCSS() {
        modeLabel.getStyleClass().clear();
        modeLabel.getStyleClass().add("");
        setModeLabelState(modeLabel, false);
    }

    @FXML
    void addOrEditButtonClick(ActionEvent event) {

    }

    @FXML
    void applyButtonClick(ActionEvent event) {

    }

    @FXML
    void cancelButtonClick(ActionEvent event) {

    }

    @FXML
    void deleteButtonClick(ActionEvent event) {

    }

    @FXML
    void technicTableViewAction(KeyEvent event) {

    }

    @Override
    public void setInformation() {
        fillTechnicTableView();
        fillComboBoxes();
        setControlsValues();
    }

    private void fillComboBoxes() {
        typeComboBox.getItems().clear();
        typeComboBox.getItems().addAll(AppData.getListOfObjects(AppData.getTypes(), false));

        statusComboBox.getItems().clear();
        statusComboBox.getItems().addAll(AppData.getListOfObjects(AppData.getStatuses(), false));

        ownerComboBox.getItems().clear();
        ownerComboBox.getItems().addAll(Employee.sortEmployeeList(AppData.getListOfObjects(AppData.getEmployeesWithoutEmptyObject(), false)));
    }

    private void setControlsValues() {
        selectedRecord=technicTableView.getSelectionModel().getSelectedItem();
//        idTextField.setText();
    }

    private void fillTechnicTableView() {
        technicTableView.setItems(AppData.getListOfObjects(AppData.getTechnic(),false));
        technicTableView.getSelectionModel().select(0);
        technicTableView.requestFocus();
        updateCountLabel();
    }

    private void updateCountLabel() {
        countLabel.setText("Количество оборудования: " + AppData.getListOfObjects(AppData.getTechnic(),false).size());
    }

    @Override
    public void saveInformationToIni() {

    }

    @Override
    public int getDataHash() {
        return 0;
    }
}
