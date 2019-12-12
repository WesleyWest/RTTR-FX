package GUI.Settings.divisions;

import GUI.Settings.SettingsPaneController;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import objects.BL.AppData;
import objects.BL.Employees.Division;
import objects.GUI.GUIData;

import java.util.ArrayList;
import java.util.Optional;

public class DivisionsSettingsController extends SettingsPaneController {

    @FXML
    private TableView<Division> divisionsTableView;
    @FXML
    TableColumn<Division, String> codeColumn;
    @FXML
    TableColumn<Division, String> fullDescriptionColumn;
    @FXML
    private TextField codeTextField;
    @FXML
    private TextField fullDescriptionTextField;
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

    private static Division selectedRecord;
    private int indexOfSelectedRecord;


    @FXML
    void initialize() {

        initListeners();
        applyCSS();

        codeColumn.setCellValueFactory(new PropertyValueFactory<Division, String>("code"));
        fullDescriptionColumn.setCellValueFactory(new PropertyValueFactory<Division, String>("description"));
        divisionsTableView.setItems(AppData.getListOfObjects(AppData.getDivisions(),false));
        divisionsTableView.getSelectionModel().select(0);
        divisionsTableView.requestFocus();
        selectedRecord = divisionsTableView.getSelectionModel().getSelectedItem();
        secondButtonBar.setVisible(false);
        setFieldsValues(selectedRecord);
        updateCountLabel();
    }


    void applyCSS() {
        modeLabel.getStyleClass().set(0, "label-view-mode");
    }


    private void setFieldsValues(Division division) {
        codeTextField.setText(division.getCode());
        fullDescriptionTextField.setText(division.getDescription());
    }

    private void initListeners() {
        AppData.getDivisions().addListener((ListChangeListener<Division>) c -> updateCountLabel());

        divisionsTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                divisionsTableViewAction(event);
            }
            if (event.getClickCount() == 2) {
                editButton.fire();
            }
        });
    }

    public void divisionsTableViewAction(Event event) {
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                editButton.fire();
            }
        }
        selectedRecord = divisionsTableView.getSelectionModel().getSelectedItem();
        setFieldsValues(selectedRecord);

    }

    private void updateCountLabel() {
        countLabel.setText("Количество подразделений: " + AppData.getListOfObjects(AppData.getDivisions(),false).size());
    }

    @FXML
    void addOrEditButtonClick(ActionEvent event) {
        getParentController().setExitButtonVisible(false);
        getParentController().setTabsDisabled("001000");
        allControlsSetEditable(true);

        Button callerButton = (Button) event.getSource();
        if (callerButton.getId().equals("addButton")) {
            applyButton.setText("Добавить");
            setNewDivisionValuesToControls();
        } else {
            applyButton.setText("Применить");
            indexOfSelectedRecord = divisionsTableView.getSelectionModel().getSelectedIndex();
            codeTextField.requestFocus();
        }
    }

    private void setNewDivisionValuesToControls() {
        codeTextField.setText("");
        fullDescriptionTextField.setText("");
        applyButton.requestFocus();
    }

    private void allControlsSetEditable(boolean state) {
        setModeLabelState(modeLabel, state);
        codeTextField.setEditable(state);
        fullDescriptionTextField.setEditable(state);
        mainButtonBar.setVisible(!state);
        secondButtonBar.setVisible(state);
        divisionsTableView.setDisable(state);
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
        int id = selectedRecord.getID();
        String code = codeTextField.getText();
        String description = fullDescriptionTextField.getText();
        Division division = new Division(id, code, description, false);

        ArrayList<String> args = new ArrayList<>();
        args.add(code);
        args.add(description);

        if (GUIData.isNotEmpty(args)) {
            Button button = (Button) event.getSource();
            if (button.getText().equals("Добавить")) {
                AppData.getDb().handleDivision(division, true);
                AppData.getDivisions().add(division);
                indexOfSelectedRecord = AppData.getUsers().size() - 1;
            } else {
                AppData.getDb().handleDivision(division, false);
                AppData.getDivisions().set(indexOfSelectedRecord, division);
                divisionsTableView.getItems().set(indexOfSelectedRecord, division);
            }
            cancelButton.fire();
            divisionsTableView.getSelectionModel().select(indexOfSelectedRecord);
            divisionsTableView.requestFocus();
            selectedRecord = divisionsTableView.getSelectionModel().getSelectedItem();
            setFieldsValues(selectedRecord);
        }
    }

    @FXML
    void deleteButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Вы уверены, что хотите удалить запись: ");
        alert.setContentText("Запись: <" + selectedRecord.getCode() + "> " + selectedRecord.getDescription() + "?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            AppData.getDivisions().remove(selectedRecord);
            AppData.getDb().markRecordAsDeleted("employee_divisions",
                    "division_isdeleted",
                    "division_id",
                    selectedRecord.getID());
        }

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



