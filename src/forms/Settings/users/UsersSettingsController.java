package forms.Settings.users;

import forms.Settings.SettingsPaneController;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import objects.BL.AppData;
import objects.BL.Users.Role;
import objects.BL.Users.User;

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
    private PasswordField userPasswordField;

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



    User selectedRecord;

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
        usersTableView.requestFocus();
        usersTableView.getSelectionModel().select(0);
        selectedRecord = usersTableView.getSelectionModel().getSelectedItem();
        passwordTextField.setVisible(false);
        secondButtonBar.setVisible(false);
        setFieldsValues(AppData.getUsers().get(0));
        initListeners();
        updateCountLabel();

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
        userPasswordField.setText(user.getPassword());
        roleComboBox.getSelectionModel().select(
                roleComboBox.getItems().indexOf(user.getRole()));

        if (user.getRealStatus()) {
            enabledRadioButton.setSelected(true);
        } else {
            disabledRadioButton.setSelected(true);
        }
    }

    private void initListeners() {
        AppData.getUsers().addListener((ListChangeListener<User>) c -> updateCountLabel());

        usersTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                usersTableViewAction(event);
            }
            if (event.getClickCount() == 2) {
//                    mainEditButton.fire();
            }
        });
    }

    public void usersTableViewAction(Event event) {

        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
//                mainEditButton.fire();
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
            passwordTextField.setText(userPasswordField.getText());
            passwordTextField.setVisible(true);
        } else {
            showPasswordToggleButton.setText("Отобразить пароль");
            passwordTextField.setEditable(false);
            passwordTextField.setVisible(false);
        }
    }

    @FXML
    void addOrEditButtonClick(ActionEvent event){
        startUserAddingOrEditing(event);
    }

    private void startUserAddingOrEditing(ActionEvent event) {
        Button callerButton = (Button) event.getSource();

        if (callerButton.getId().equals("addButton")){
            applyButton.setText("Добавить");
        } else {
            applyButton.setText("Применить");
        }
        mainButtonBar.setVisible(false);
        secondButtonBar.setVisible(true);
        getParentController().setExitButtonDisable(false);

    }

    @FXML
    void cancelButtonClick(){
        mainButtonBar.setVisible(true);
        secondButtonBar.setVisible(false);
        getParentController().setExitButtonDisable(true);
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



