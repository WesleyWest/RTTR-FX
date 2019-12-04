package GUI.Settings.users;

import GUI.Settings.SettingsPaneController;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import objects.BL.AppData;
import objects.BL.Employees.Employee;
import objects.BL.Users.Role;
import objects.BL.Users.User;
import objects.GUI.GUIData;

import java.io.IOException;
import java.util.ArrayList;

public class UsersSettingsController extends SettingsPaneController {

    @FXML
    private TableView<User> usersTableView;
    @FXML
    TableColumn<User, Integer> idColumn;
    @FXML
    TableColumn<User, String> nameColumn;
    @FXML
    TableColumn<User, String> roleColumn;
    @FXML
    TableColumn<User, String> statusColumn;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField nameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private ComboBox roleComboBox;

    @FXML
    private RadioButton enabledRadioButton;

    @FXML
    private ToggleGroup statusGroup;

    @FXML
    private RadioButton disabledRadioButton;

    @FXML
    private ToggleButton showPasswordToggleButton;

    @FXML
    private Button addButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button applyButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label countLabel;

    @FXML
    private Label modeLabel;

    @FXML
    private ButtonBar mainButtonBar;

    @FXML
    private ButtonBar secondButtonBar;

    @FXML
    private ComboBox employeesComboBox;

    private static User selectedRecord;
    private int indexOfSelectedRecord;

    @FXML
    void initialize() {
        applyCSS();
        for (Role role : Role.values()) {
            roleComboBox.getItems().add(role.getRoleName());
        }
        idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("ID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
        usersTableView.setItems(AppData.getUsers());
        usersTableView.getSelectionModel().select(0);
        usersTableView.requestFocus();
        selectedRecord = usersTableView.getSelectionModel().getSelectedItem();

        fillEmployeesComboBox();
        passwordTextField.setVisible(false);
        secondButtonBar.setVisible(false);
        setFieldsValues(AppData.getUsers().get(0));
        initListeners();
        updateCountLabel();
    }

    public void fillEmployeesComboBox() {
        employeesComboBox.getItems().clear();
        ArrayList<Employee> tmpEmployees = new ArrayList<>(AppData.getEmployees());

        for (Employee tmpEmployee : sortEmployeeList(tmpEmployees)) {
            employeesComboBox.getItems().add(tmpEmployee);
        }
    }

    private ArrayList<Employee> sortEmployeeList(ArrayList<Employee> tmpEmployees) {
        employeesComboBox.getItems().add(tmpEmployees.get(0));
        tmpEmployees.remove(0);
        tmpEmployees.sort((Employee o1, Employee o2) -> {
            int tmp1 =
                    o1.getDivision().getDescription().charAt(0) * -100
                            + o1.getPosition().getID() * 10
                            + o1.getLastName().charAt(0);
            int tmp2 =
                    o2.getDivision().getDescription().charAt(0) * -100
                            + o2.getPosition().getID() * 10
                            + o2.getLastName().charAt(0);
            return tmp2 - tmp1;
        });
        return tmpEmployees;
    }

    void applyCSS() {
        modeLabel.getStyleClass().set(0, "label-view-mode");
    }


    private void setFieldsValues(User user) {
        if (passwordTextField.isVisible()) {
            showPasswordToggleButton.fire();
        }
        idTextField.setText(user.getID().toString());
        nameTextField.setText(user.getName());
        passwordField.setText(user.getPassword());
        roleComboBox.getSelectionModel().select(
                roleComboBox.getItems().indexOf(user.getRole()));

        if (user.isActive()) {
            enabledRadioButton.setSelected(true);
        } else {
            disabledRadioButton.setSelected(true);
        }
        employeesComboBox.getSelectionModel().select(user.getEmployee());
        deleteButton.setDisable(selectedRecord.isUndeletable());
        editButton.setDisable(selectedRecord.isUndeletable());
    }

    private void initListeners() {
        AppData.getUsers().addListener((ListChangeListener<User>) c -> updateCountLabel());

        usersTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                usersTableViewAction(event);
            }
            if (event.getClickCount() == 2) {
                editButton.fire();
            }
        });
    }

    public void usersTableViewAction(Event event) {
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                editButton.fire();
            }
        }
        selectedRecord = usersTableView.getSelectionModel().getSelectedItem();
        setFieldsValues(selectedRecord);
    }

    private void updateCountLabel() {
        countLabel.setText("Количество пользователей: " + AppData.getUsers().size());
    }

    public void showPasswordToggleButtonClick(ActionEvent event) {
        if (showPasswordToggleButton.isSelected()) {
            showPasswordToggleButton.setText("Скрыть пароль");
            passwordTextField.setText(passwordField.getText());
            passwordTextField.setVisible(true);
        } else {
            showPasswordToggleButton.setText("Отобразить пароль");
            passwordTextField.setEditable(false);
            passwordTextField.setVisible(false);
        }
    }

    @FXML
    void addOrEditButtonClick(ActionEvent event) {
        getParentController().setExitButtonVisible(false);
        getParentController().setTabsDisabled("010000");
        allControlsSetEditable(true);

        Button callerButton = (Button) event.getSource();
        if (callerButton.getId().equals("addButton")) {
            applyButton.setText("Добавить");
            setNewUserValuesToControls();
        } else {
            applyButton.setText("Применить");
            indexOfSelectedRecord = usersTableView.getSelectionModel().getSelectedIndex();
        }
        nameTextField.requestFocus();
    }

    private void setNewUserValuesToControls() {
        int nextID = AppData.getDb().getLastSequenceNumber("users");
        idTextField.setText(String.valueOf(nextID));
        nameTextField.setText("");
        passwordField.setText("");
        roleComboBox.getSelectionModel().select(2);
        enabledRadioButton.fire();
    }

    private void allControlsSetEditable(boolean state) {
        mainButtonBar.setVisible(!state);
        secondButtonBar.setVisible(state);
        usersTableView.setDisable(state);

        passwordTextField.setVisible(false);
        nameTextField.setEditable(state);
        passwordField.setEditable(state);
        roleComboBox.setDisable(!state);
        disabledRadioButton.setDisable(!state);
        enabledRadioButton.setDisable(!state);
        employeesComboBox.setDisable(!state);
        setModeLabelState(modeLabel, state);
    }

    @FXML
    void cancelButtonClick() {
        getParentController().setExitButtonVisible(true);
        getParentController().setTabsDisabled("111111");
        allControlsSetEditable(false);
        setFieldsValues(selectedRecord);
    }

    @FXML
    void applyButtonClick(ActionEvent event) {
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        String password = passwordField.getText();
        Role role = Role.roleByName(
                (String) roleComboBox.getSelectionModel().getSelectedItem());
        boolean status = enabledRadioButton.isSelected();

        Employee employee = (Employee) employeesComboBox.getSelectionModel().getSelectedItem();
        User user = new User(id, name, password, role, status, false, employee, false);
        Button button = (Button) event.getSource();
        if (button.getText().equals("Добавить")) {
            AppData.getDb().handleUser(user, true);
            AppData.getUsers().add(user);
            indexOfSelectedRecord = AppData.getUsers().size() - 1;
        } else {
            AppData.getDb().handleUser(user, false);
            AppData.getUsers().set(indexOfSelectedRecord, user);
        }
        cancelButton.fire();
        usersTableView.getSelectionModel().select(indexOfSelectedRecord);
        usersTableView.requestFocus();
        setFieldsValues(AppData.getUsers().get(indexOfSelectedRecord));
    }

    @FXML
    void deleteButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        selectedRecord = usersTableView.getSelectionModel().getSelectedItem();
        try {
            Parent root = loader.load(getClass().getResource("RemoveConfirmation.fxml").openStream());
            GUIData.openCustomWindow(event, root, 250, 128, Modality.APPLICATION_MODAL, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static User getUserToDelete() {
        return selectedRecord;
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



