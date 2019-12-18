package GUI.Settings.employees;

import GUI.Settings.SettingsPaneController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import objects.BL.AppData;
import objects.BL.Employees.Division;
import objects.BL.Employees.Employee;
import objects.BL.Employees.Position;
import objects.BL.Users.User;
import objects.GUI.GUIData;

import java.util.ArrayList;

public class EmployeesSettingsController extends SettingsPaneController {

    @FXML
    private TableView<Employee> employeesTableView;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> positionColumn;

    @FXML
    private TableColumn<Employee, String> divisionColumn;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField middleNameTextField;

    @FXML
    private ComboBox<Position> positionComboBox;

    @FXML
    private ComboBox<Division> divisionComboBox;

    @FXML
    private ButtonBar mainButtonBar;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label countLabel;

    @FXML
    private Label modeLabel;

    @FXML
    private ButtonBar secondButtonBar;

    @FXML
    private Button applyButton;

    @FXML
    private Button cancelButton;

    private static Employee selectedRecord;
    private int indexOfSelectedRecord;

    @FXML
    void initialize() {
        initListeners();
        applyCSS();
        nameColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("fullName"));
        positionColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("stringPosition"));
        divisionColumn.setCellValueFactory(new PropertyValueFactory<Employee, String>("stringDivision"));
        fillEmployeesTableView();
        selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();
        secondButtonBar.setVisible(false);
        fillComboBoxes();
        setControlsValues(selectedRecord);
    }


    private void fillEmployeesTableView() {
        ObservableList<Employee> tmpEmployees = FXCollections.observableArrayList(
                AppData.getListOfObjects(AppData.getEmployeesWithoutEmptyObject(), false));
        employeesTableView.setItems(Employee.sortEmployeeList(tmpEmployees));
        employeesTableView.getSelectionModel().select(0);
        employeesTableView.requestFocus();
        updateCountLabel();
    }

    private void fillComboBoxes() {
        positionComboBox.getItems().clear();
        positionComboBox.getItems().addAll(AppData.getListOfObjects(AppData.getPositions(), false));

        divisionComboBox.getItems().clear();
        divisionComboBox.getItems().addAll(AppData.getListOfObjects(AppData.getDivisions(), false));
    }

    private void setControlsValues(Employee employee) {
        lastNameTextField.setText(employee.getLastName());
        nameTextField.setText(employee.getName());
        middleNameTextField.setText(employee.getMiddleName());
        positionComboBox.getSelectionModel().select(employee.getPosition());
        divisionComboBox.getSelectionModel().select(employee.getDivision());
    }

    private void applyCSS() {
        modeLabel.getStyleClass().clear();
        modeLabel.getStyleClass().add("");
        setModeLabelState(modeLabel, false);
    }

    private void initListeners() {
        employeesTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                employeesTableViewAction(event);
            }
            if (event.getClickCount() == 2) {
                editButton.fire();
            }
        });
    }

    public void employeesTableViewAction(Event event) {
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                editButton.fire();
            }
        }
        selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();
        setControlsValues(selectedRecord);
    }

    private void updateCountLabel() {
        countLabel.setText("Количество сотрудников: " + (employeesTableView.getItems().size()));
    }

    @FXML
    void addOrEditButtonClick(ActionEvent event) {
        allControlsSetEditable(true);
        Button callerButton = (Button) event.getSource();

        if (callerButton.getId().equals("addButton")) {
            applyButton.setText("Добавить");
            setNewEmployeeValuesToControls();
        } else {
            applyButton.setText("Применить");
            indexOfSelectedRecord = employeesTableView.getSelectionModel().getSelectedIndex();
        }
        lastNameTextField.requestFocus();
    }

    private void setNewEmployeeValuesToControls() {
        lastNameTextField.setText("");
        nameTextField.setText("");
        middleNameTextField.setText("");
        positionComboBox.getItems().add(new Position(-1, "Должность не определена", 0, false));
        divisionComboBox.getItems().add(new Division(-1, "Подразделение не определено", "null", false));
        positionComboBox.getSelectionModel().select(positionComboBox.getItems().size() - 1);
        divisionComboBox.getSelectionModel().select(divisionComboBox.getItems().size() - 1);
    }

    private void allControlsSetEditable(boolean state) {
        getParentController().setExitButtonVisible(!state);
        String tabsKey = (state) ? "000010" : "111111";
        mainButtonBar.setVisible(!state);
        secondButtonBar.setVisible(state);
        getParentController().setTabsDisabled(tabsKey);
        nameTextField.setEditable(state);
        lastNameTextField.setEditable(state);
        middleNameTextField.setEditable(state);
        employeesTableView.setDisable(state);
        divisionComboBox.setDisable(!state);
        positionComboBox.setDisable(!state);
        setModeLabelState(modeLabel, state);
    }

    @FXML
    void applyButtonClick(ActionEvent event) {

        String lastName = lastNameTextField.getText();
        String name = nameTextField.getText();
        String middleName = middleNameTextField.getText();
        Position position = positionComboBox.getSelectionModel().getSelectedItem();
        Division division = divisionComboBox.getSelectionModel().getSelectedItem();
        if (position.getID() != -1 || division.getID() != -1) {
            ArrayList<String> args = new ArrayList<>();
            args.add(name);
            args.add(lastName);
            if (GUIData.isNotEmpty(args)) {
                Employee employee = new Employee(selectedRecord.getID(), lastName, name, middleName, position, division, false);
                Button button = (Button) event.getSource();
                if (button.getText().equals("Добавить")) {
                    AppData.getDb().handleEmployee(employee, true);
                    AppData.getEmployees().add(employee);
                    indexOfSelectedRecord = AppData.getEmployees().size() - 1;
                } else {
                    AppData.getDb().handleEmployee(employee, false);
                    AppData.getEmployees().set(indexOfSelectedRecord, employee);
                }
                cancelButton.fire();
                fillEmployeesTableView();
                employeesTableView.getSelectionModel().select(indexOfSelectedRecord);
                employeesTableView.requestFocus();
                setControlsValues(AppData.getEmployees().get(indexOfSelectedRecord));
            }

        } else {
            GUIData.showAlert("Должность и подразеление должны быть выбраны");
        }

    }

    @FXML
    void cancelButtonClick(ActionEvent event) {
        if (applyButton.getText().equals("Добавить")) {
            positionComboBox.getItems().remove(positionComboBox.getItems().size() - 1);
            divisionComboBox.getItems().remove(divisionComboBox.getItems().size() - 1);
        }
        allControlsSetEditable(false);
    }

    @FXML
    void deleteButtonClick(ActionEvent event) {

    }

    @Override
    public void setInformation() {

    }

    @Override
    public void saveInformationToIni() {

    }

    @Override
    public int getDataHash() {
        return 0;
    }
}
