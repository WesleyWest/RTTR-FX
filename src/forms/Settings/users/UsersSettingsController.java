package forms.Settings.users;
import forms.Settings.SettingsPaneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import objects.BL.Users.User;

public class UsersSettingsController extends SettingsPaneController {

    @FXML
    private AnchorPane usersAnchorPane;

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
    private ComboBox<?> roleComboBox;

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

    @FXML
    void initialize(){

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



