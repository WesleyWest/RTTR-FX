package forms.Settings.users;

import forms.Settings.SettingsPaneController;
import javafx.collections.ListChangeListener;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
    private Label countLabel;

    User selectedRecord;

    @FXML
    void initialize() {

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
        selectedRecord=usersTableView.getSelectionModel().getSelectedItem();
        setFieldsValues(AppData.getUsers().get(0));
//        initListeners();
        updateCountLabel();

    }

    private void setFieldsValues(User user) {
        passwordTextField.setVisible(false);
        /*if (openPasswordField.isVisible()) {
            openPasswordField.setVisible(false);
            toggleButtonPassword.fire();
        }*/

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
        AppData.getUsers().addListener(new ListChangeListener<User>() {
            @Override
            public void onChanged(Change<? extends User> c) {
                updateCountLabel();
            }
        });

        usersTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 1) {
                    usersTableViewAction(event);
                }
                if (event.getClickCount() == 2) {
//                    mainEditButton.fire();
                }

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
        countLabel.setText("Count of records: " + AppData.getUsers().size());
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



