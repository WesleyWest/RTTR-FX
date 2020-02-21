package GUI.Settings.technic;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.BL.AppData;
import objects.BL.SimpleObject;
import objects.GUI.GUIData;

import java.util.ArrayList;
import java.util.Optional;

public class ListEditingController {

    @FXML
    private AnchorPane pane;

    @FXML
    private TextField objectTextField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button applyButton;

    @FXML
    private TableView<SimpleObject> objectsTableView;

    @FXML
    private TableColumn<SimpleObject, String> descriptionColumn;

    private ObservableList simpleObjectsList;

    private String referenceDescription;
    private String simpleObjectType;
    private SimpleObject handledRecord = null;
    private TechnicSettingsController parentController;

    @FXML
    void initialize() {
        applyCSS();
        initListeners();
    }

    private void applyCSS() {
        pane.getStyleClass().add("anchor-pane-main");
    }

    private void initListeners() {
        objectsTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                objectsTableViewAction(event);
            }
            if (event.getClickCount() == 2) {
                objectTextField.requestFocus();
            }
        });
    }

    @FXML
    void objectTextFieldChange(Event event) {
        applyButton.setDisable(objectTextField.getText().equals(referenceDescription));
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                if (!applyButton.isDisabled()) {
                    applyButton.fire();
                }
            }
            if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                Stage oldStage = (Stage) objectTextField.getScene().getWindow();
                oldStage.close();
            }
        }
    }

    @FXML
    void addEmptyRecord(ActionEvent event) {
        String tableName = (simpleObjectType.equals("type")) ? "technic_types" : "technic_statuses";
        int id = AppData.getDb().getLastSequenceNumber(tableName);
        handledRecord = new SimpleObject<>(id, "Новая запись", false);
        simpleObjectsList.add(handledRecord);
        objectsTableView.scrollTo(handledRecord);
        objectsTableView.getSelectionModel().select(handledRecord);
        setControlsValues();
        objectTextField.requestFocus();
    }

    @FXML
    void applyChanges(ActionEvent event) {
        ArrayList<String> args = new ArrayList<>();
        args.add(objectTextField.getText());
        if (GUIData.isNotEmpty(args)) {
            boolean isNew = true;
            int index = 0;
            if (handledRecord == null) {
                handledRecord = objectsTableView.getSelectionModel().getSelectedItem();
                isNew = false;
            }
            if (simpleObjectType.equals("type")) {
                index = AppData.getTypes().indexOf(handledRecord);
                handledRecord.setDescription(objectTextField.getText());
                AppData.getTypes().set(index, handledRecord);
            } else {
                index = AppData.getStatuses().indexOf(handledRecord);
                handledRecord.setDescription(objectTextField.getText());
                AppData.getStatuses().set(index, handledRecord);
            }
            AppData.getDb().handleSimpleObject(handledRecord, isNew, simpleObjectType);
            applyButton.setDisable(true);
            referenceDescription = handledRecord.getDescription();
            handledRecord = null;
            parentController.fillComboBoxes();
            parentController.setControlsValues();
        }
    }

    @FXML
    void deleteRecord(ActionEvent event) {
        SimpleObject objectToRemove = objectsTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Вы уверены, что хотите удалить запись: ");
        alert.setContentText(objectToRemove.getDescription());
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            String tableName = "";
            String fieldPreffix = "";
            int index = objectsTableView.getSelectionModel().getSelectedIndex();
            if (index != 0) {
                objectsTableView.getSelectionModel().select(index - 1);
            } else {
                objectsTableView.getSelectionModel().select(1);
            }
            if (simpleObjectType.equals("type")) {
                tableName = "technic_types";
                fieldPreffix = "technic_type";
                AppData.getTypes().remove(objectToRemove);
            } else {
                tableName = "technic_statuses";
                fieldPreffix = "technic_status";
                AppData.getTypes().remove(objectToRemove);
            }
            AppData.getDb().markRecordAsDeleted(tableName,
                    fieldPreffix + "_isdeleted",
                    fieldPreffix + "_id",
                    objectToRemove.getID());
        }
    }

    @FXML
    void objectsTableViewAction(Event event) {
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                objectTextField.requestFocus();
            }
            if (keyEvent.getCode().equals(KeyCode.ESCAPE)) {
                Stage oldStage = (Stage) objectsTableView.getScene().getWindow();
                oldStage.close();
            }
        }
        setControlsValues();
    }

    private void setControlsValues() {
        referenceDescription = objectsTableView.getSelectionModel().getSelectedItem().getDescription();
        objectTextField.setText(referenceDescription);
    }

    protected <T extends SimpleObject> void setSimpleObjectsList(ObservableList<T> list, String simpleObjectType) {
        this.simpleObjectType = simpleObjectType;
        simpleObjectsList = list;
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<SimpleObject, String>("description"));
        objectsTableView.setItems(simpleObjectsList);
        objectsTableView.getSelectionModel().selectFirst();
        setControlsValues();
    }

    protected void setParentController(TechnicSettingsController controller){
        this.parentController=controller;
    }

}



