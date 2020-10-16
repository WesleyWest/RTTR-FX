package GUI.Settings.technic;

import GUI.Settings.SettingsPaneController;
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
import objects.BL.SimpleObject;
import objects.BL.Technic.Technic;
import objects.BL.Technic.TechnicStatus;
import objects.BL.Technic.TechnicType;
import objects.GUI.GUIData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

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
    private ComboBox<SimpleObject<TechnicType>> typeComboBox;

    @FXML
    private ComboBox<SimpleObject<TechnicStatus>> statusComboBox;

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
    void initialize() {
        initListeners();
        applyCSS();
        nameColumn.setCellValueFactory(new PropertyValueFactory<Technic, String>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Technic, String>("stringType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Technic, String>("stringStatus"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<Technic, String>("stringOwner"));
        secondButtonBar.setVisible(false);
    }

    private void initListeners() {
        technicTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                technicTableViewAction(event);
            }
            if (event.getClickCount() == 2) {
                editButton.fire();
            }
        });
    }

    private void applyCSS() {
        modeLabel.getStyleClass().clear();
        modeLabel.getStyleClass().add("");
        setModeLabelState(modeLabel, false);
    }

    @FXML
    public void editListButtonClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(getClass().getResource("ListEditing.fxml").openStream());
            ListEditingController childController = loader.getController();
            childController.setParentController(this);
            Button callButton = (Button) event.getSource();
            if (callButton.getId().equals("editTypeListButton")) {
                childController.setSimpleObjectsList(AppData.getTypes(),"type");
            } else {
                childController.setSimpleObjectsList(AppData.getStatuses(),"status");
            }
            GUIData.openCustomWindow(event, root, 312, 267, Modality.APPLICATION_MODAL, false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addOrEditButtonClick(ActionEvent event) {
        allControlsSetEditable(true);
        Button callerButton = (Button) event.getSource();

        if (callerButton.getId().equals("addButton")) {
            applyButton.setText("Добавить");
            setNewTechnicValuesToControls();
        } else {
            applyButton.setText("Применить");
        }
        nameTextField.requestFocus();
    }

    private void setNewTechnicValuesToControls() {
        int nextID = AppData.getDb().getLastSequenceNumber("technic");
        idTextField.setText(String.valueOf(nextID));
        nameTextField.setText("");
        descriptionTextField.setText("");
        statusComboBox.getSelectionModel().select(0);
        typeComboBox.getSelectionModel().select(0);
        ownerComboBox.getSelectionModel().select(AppData.getUser().getEmployee());
    }

    private void allControlsSetEditable(boolean state) {
        getParentController().setExitButtonVisible(!state);
        mainButtonBar.setVisible(!state);
        secondButtonBar.setVisible(state);
        String tabsKey = (state) ? "000001" : "111111";
        getParentController().setTabsDisabled(tabsKey);
        nameTextField.setEditable(state);
        descriptionTextField.setEditable(state);
        technicTableView.setDisable(state);
        statusComboBox.setDisable(!state);
        typeComboBox.setDisable(!state);
        ownerComboBox.setDisable(!state);
//        editStatusListButton.setDisable(!state);
//        editTypeListButton.setDisable(!state);
        setModeLabelState(modeLabel, state);
    }

    @FXML
    void applyButtonClick(ActionEvent event) {
        int id = Integer.parseInt(idTextField.getText());
        String name = nameTextField.getText();
        String description = descriptionTextField.getText();
        SimpleObject<TechnicStatus> status = statusComboBox.getSelectionModel().getSelectedItem();
        SimpleObject<TechnicType> type = typeComboBox.getSelectionModel().getSelectedItem();
        Employee owner = ownerComboBox.getSelectionModel().getSelectedItem();

        ArrayList<String> args = new ArrayList<>();
        args.add(name);
        args.add(description);
        if (GUIData.isNotEmpty(args)) {
            Technic technic = new Technic(id, name, description, status, type, owner, false);
            Button button = (Button) event.getSource();
            if (button.getText().equals("Добавить")) {
                AppData.getDb().handleTechnic(technic, true);
                AppData.getTechnic().add(technic);
                selectedRecord = technic;
            } else {
                AppData.getDb().handleTechnic(technic, false);
                AppData.setTechnic(AppData.getDb().readTechnicFromDB());
            }
            cancelButton.fire();
            fillTechnicTableView();
            selectRecordByID(selectedRecord.getID());
            technicTableView.requestFocus();
            setControlsValues();
        } else {
            GUIData.showAlert("Все поля должны быть заполнены!");
        }
    }

    private void selectRecordByID(Integer id) {
        for (Technic t : technicTableView.getItems()) {
            if (t.getID() == id) {
                technicTableView.getSelectionModel().select(t);
                return;
            }
        }
    }

    @FXML
    void cancelButtonClick(ActionEvent event) {
        allControlsSetEditable(false);
        setControlsValues();
    }

    @FXML
    void deleteButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Вы уверены, что хотите удалить запись: ");
        alert.setContentText(selectedRecord.getStringType() + " <" + selectedRecord.getName() + ">");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            AppData.getTechnic().remove(selectedRecord);
            AppData.getDb().markRecordAsDeleted("technic",
                    "technic_isdeleted",
                    "technic_id",
                    selectedRecord.getID());
            fillTechnicTableView();
            technicTableView.getSelectionModel().select(0);
            setControlsValues();
            technicTableView.requestFocus();
        }
    }

    @FXML
    void technicTableViewAction(Event event) {
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                editButton.fire();
            }
        }
        setControlsValues();
    }

    @Override
    public void setInformation() {
        fillTechnicTableView();
        fillComboBoxes();
        setControlsValues();
    }

    protected void fillComboBoxes() {
        typeComboBox.getItems().clear();
        typeComboBox.getItems().addAll(AppData.getListOfObjects(AppData.getTypes(), false));

        statusComboBox.getItems().clear();
        statusComboBox.getItems().addAll(AppData.getListOfObjects(AppData.getStatuses(), false));

        ownerComboBox.getItems().clear();
        ownerComboBox.getItems().addAll(Employee.sortEmployeeList(
                AppData.getListOfObjects(AppData.getEmployeesWithoutEmptyObject(), false)));
    }

    protected void setControlsValues() {
        selectedRecord = technicTableView.getSelectionModel().getSelectedItem();
        idTextField.setText(selectedRecord.getID().toString());
        nameTextField.setText(selectedRecord.getName());
        descriptionTextField.setText(selectedRecord.getDescription());
        typeComboBox.getSelectionModel().select(selectedRecord.getType());
        statusComboBox.getSelectionModel().select(selectedRecord.getStatus());
        ownerComboBox.getSelectionModel().select(selectedRecord.getOwner());
    }

    protected void fillTechnicTableView() {
        technicTableView.setItems(AppData.getListOfObjects(AppData.getTechnic(), false));
        technicTableView.getSelectionModel().select(0);
        technicTableView.requestFocus();
        updateCountLabel();
    }


    private void updateCountLabel() {
        countLabel.setText("Количество оборудования: " + AppData.getListOfObjects(AppData.getTechnic(), false).size());
    }

    @Override
    public void saveInformationToIni() {

    }

    @Override
    public int getDataHash() {
        return 0;
    }
}
