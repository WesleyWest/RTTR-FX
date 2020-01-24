package GUI.Settings.technic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import objects.BL.AppData;
import objects.BL.SimpleObject;

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
    }

    @FXML
    void addEmptyRecord(ActionEvent event) {
//        SimpleObject object = AppData.getObjectByID()
    }

    @FXML
    void applyChanges(ActionEvent event) {

    }

    @FXML
    void deleteRecord(ActionEvent event) {

    }

    @FXML
    void objectsTableViewAction(Event event) {
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                setControlsValues();
            }
        }
        setControlsValues();
    }

    private void setControlsValues() {
        referenceDescription = objectsTableView.getSelectionModel().getSelectedItem().getDescription();
        objectTextField.setText(referenceDescription);
    }

    protected <T extends SimpleObject> void setSimpleObjectsList(ObservableList<T> list) {
        simpleObjectsList = FXCollections.observableArrayList(list);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<SimpleObject, String>("description"));
        objectsTableView.setItems(simpleObjectsList);
        objectsTableView.getSelectionModel().selectFirst();
        setControlsValues();
    }

}



