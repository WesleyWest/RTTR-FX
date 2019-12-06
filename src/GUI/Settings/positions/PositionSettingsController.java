package GUI.Settings.positions;

import GUI.Settings.SettingsPaneController;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import objects.BL.AppData;
import objects.BL.Employees.Position;
import objects.BL.SimpleObject;

import java.util.ArrayList;

public class PositionSettingsController extends SettingsPaneController {
    @FXML
    private TableView<SimpleObject<Position>> positionsTableView;

    @FXML
    private TableColumn<SimpleObject<Position>, String> descriptionColumn;

    @FXML
    private TextField descriptionTextField;

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
    private Button upButton;

    @FXML
    private Button downButton;

    private static SimpleObject<Position> selectedRecord;
    private int indexOfSelectedRecord;


    @FXML
    void initialize() {
        AppData.setPositions(Position.getSortedList(AppData.getPositions()));
        initListeners();
        applyCSS();
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<SimpleObject<Position>, String>("description"));
        positionsTableView.setItems(AppData.getListWithoutDeletedObjects(AppData.getPositions()));
        positionsTableView.getSelectionModel().select(0);
        positionsTableView.requestFocus();
        selectedRecord = positionsTableView.getSelectionModel().getSelectedItem();
        secondButtonBar.setVisible(false);
        directionButtonsSetDisable(true);
        setFieldsValues(selectedRecord);
        updateCountLabel();
    }

    private void directionButtonsSetDisable(boolean state) {
        upButton.setDisable(state);
        downButton.setDisable(state);
    }

    private void setFieldsValues(SimpleObject<Position> selectedRecord) {
        descriptionTextField.setText(selectedRecord.getDescription());
    }


    private void applyCSS() {
        modeLabel.getStyleClass().set(0, "label-view-mode");
        upButton.getStyleClass().set(0, "arrow-up-button");
        downButton.getStyleClass().set(0, "arrow-down-button");
    }

    private void initListeners() {
        AppData.getPositions().addListener((ListChangeListener<SimpleObject>) c -> updateCountLabel());

        positionsTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                positionsTableViewAction(event);
            }
            if (event.getClickCount() == 2) {
                editButton.fire();
            }
        });
    }

    private void updateCountLabel() {
        countLabel.setText("Количество зарегистрированных должностей: "
                + AppData.getListWithoutDeletedObjects(AppData.getPositions()).size());
    }

    @FXML
    void addOrEditButtonClick(ActionEvent event) {
        getParentController().setExitButtonVisible(false);
        getParentController().setTabsDisabled("000100");
        allControlsSetEditable(true);
        Button callerButton = (Button) event.getSource();
        if (callerButton.getId().equals("addButton")) {
            applyButton.setText("Добавить");
            descriptionTextField.setText("");
        } else {
            applyButton.setText("Применить");
            indexOfSelectedRecord = positionsTableView.getSelectionModel().getSelectedIndex();
            descriptionTextField.requestFocus();
        }
    }

    private void allControlsSetEditable(boolean state) {
        setModeLabelState(modeLabel, state);
        descriptionTextField.setEditable(true);
        mainButtonBar.setVisible(!state);
        secondButtonBar.setVisible(state);
        positionsTableView.setDisable(state);
        upButton.setDisable(!state);
        downButton.setDisable(!state);
    }

    @FXML
    void applyButtonClick(ActionEvent event) {

    }

    @FXML
    void cancelButtonClick(ActionEvent event) {
        getParentController().setExitButtonVisible(true);
        getParentController().setTabsDisabled("111111");
        allControlsSetEditable(false);
        setFieldsValues(selectedRecord);
    }

    @FXML
    void deleteButtonClick(ActionEvent event) {

    }

    @FXML
    void positionsTableViewAction(Event event) {
        if (event.getEventType().equals(KeyEvent.KEY_RELEASED)) {
            KeyEvent keyEvent = (KeyEvent) event;
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                editButton.fire();
            }
        }
        selectedRecord = positionsTableView.getSelectionModel().getSelectedItem();
        setFieldsValues(selectedRecord);
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


