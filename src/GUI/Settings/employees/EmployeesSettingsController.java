package GUI.Settings.employees;

import GUI.Settings.SettingsPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import objects.BL.Employees.Employee;

public class EmployeesSettingsController extends SettingsPaneController {

    @FXML
    private TableView<Employee> usersTableView;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> roleColumn;

    @FXML
    private TableColumn<Employee, String> statusColumn;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private ComboBox<?> roleComboBox;

    @FXML
    private ComboBox<?> employeesComboBox;

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
