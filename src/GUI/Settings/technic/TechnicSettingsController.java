package GUI.Settings.technic;
import GUI.Settings.SettingsPaneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class TechnicSettingsController extends SettingsPaneController {

    @FXML
    private Label countLabel;

    @FXML
    private TableView<?> technicTableView;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> positionColumn;

    @FXML
    private TableColumn<?, ?> nameColumn1;

    @FXML
    private TableColumn<?, ?> nameColumn11;

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
    void employeesTableViewAction(MouseEvent event) {

    }

    @FXML
    void technicTableViewAction(KeyEvent event) {

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
