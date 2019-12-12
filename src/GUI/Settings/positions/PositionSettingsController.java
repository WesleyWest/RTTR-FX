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
import java.util.Optional;

public class PositionSettingsController extends SettingsPaneController {
    @FXML
    private TableView<Position> positionsTableView;

    @FXML
    private TableColumn<Position, String> descriptionColumn;

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

    private Position selectedRecord;
    private Position tmpPosition;
    private ArrayList<Position> tmpList = new ArrayList<>();

    private int indexOfSelectedRecord;

    @FXML
    void initialize() {
        initListeners();
        applyCSS();
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Position, String>("description"));
        fillTableView();
        positionsTableView.getSelectionModel().select(0);
        positionsTableView.requestFocus();
        selectedRecord = positionsTableView.getSelectionModel().getSelectedItem();
        secondButtonBar.setVisible(false);
        directionButtonsSetDisable(true);
        setFieldsValues(selectedRecord);
        updateCountLabel();
    }

    private void fillTableView() {
        positionsTableView.setItems(AppData.getListOfObjects(AppData.getPositions(), false));
        updateCountLabel();
    }

    private void directionButtonsSetDisable(boolean state) {
        upButton.setDisable(state);
        downButton.setDisable(state);
    }

    private void setFieldsValues(Position selectedRecord) {
        descriptionTextField.setText(selectedRecord.getDescription());

    }


    private void applyCSS() {
        modeLabel.getStyleClass().set(0, "label-view-mode");
        upButton.getStyleClass().set(0, "arrow-up-button");
        downButton.getStyleClass().set(0, "arrow-down-button");
    }

    private void initListeners() {
//        positionsTableView.getItems().addListener((ListChangeListener<SimpleObject>) c -> updateCountLabel());

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
                + positionsTableView.getItems().size());
    }

    @FXML
    void addOrEditButtonClick(ActionEvent event) {
        allControlsSetEditable(true);

        tmpList.clear();
        tmpList.addAll(positionsTableView.getItems());

        Button callerButton = (Button) event.getSource();
        if (callerButton.getId().equals("addButton")) {
            applyButton.setText("Добавить");
            int tmpID = AppData.getDb().getLastSequenceNumber("employee_positions");
            tmpPosition = new Position(tmpID, "Новая позиция", 0, false);
            positionsTableView.getItems().add(tmpPosition);
            positionsTableView.getSelectionModel().select(tmpPosition);
            selectedRecord = positionsTableView.getSelectionModel().getSelectedItem();
            descriptionTextField.setText("");
            positionsTableView.requestFocus();

        } else {
            applyButton.setText("Применить");
            selectedRecord = positionsTableView.getSelectionModel().getSelectedItem();
            descriptionTextField.requestFocus();
        }
    }

    private void allControlsSetEditable(boolean state) {
        getParentController().setExitButtonVisible(!state);
        String tabsKey = (state) ? "000100" : "111111";
        getParentController().setTabsDisabled(tabsKey);
        setModeLabelState(modeLabel, state);
        descriptionTextField.setEditable(true);
        mainButtonBar.setVisible(!state);
        secondButtonBar.setVisible(state);
        positionsTableView.setDisable(state);
        upButton.setDisable(!state);
        downButton.setDisable(!state);
    }

    @FXML
    void upDownButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int currentPos = positionsTableView.getSelectionModel().getSelectedIndex();
        int step = 0;
        if (clickedButton.getId().equals("upButton")) {
            if (currentPos == 0) {
                return;
            }
            step = -1;
        } else {
            if (currentPos == positionsTableView.getItems().size() - 1) {
                return;
            }
            step = 1;
        }
        int newPos = currentPos + step;
        Position tmp = positionsTableView.getItems().get(newPos);
        positionsTableView.getItems().set(newPos, positionsTableView.getItems().get(currentPos));
        positionsTableView.getItems().set(currentPos, tmp);
        positionsTableView.scrollTo(newPos);
    }

    @FXML
    void applyButtonClick(ActionEvent event) {
        positionsTableView.getItems().
                get(positionsTableView.getSelectionModel().getSelectedIndex()).
                setDescription(descriptionTextField.getText());

        ObservableList<Position> tmpPositions = FXCollections.observableArrayList();
        int tmpID = selectedRecord.getID();
        tmpPositions.addAll(AppData.getListOfObjects(AppData.getPositions(), true));
        tmpPositions.addAll(positionsTableView.getItems());

        AppData.getDb().handlePositions(tmpPositions);
        allControlsSetEditable(false);
        AppData.setPositions(AppData.getDb().readPositionsFromDB());
        positionsTableView.getItems().clear();
        fillTableView();
        positionsTableView.getSelectionModel().select(AppData.getObjectByID(AppData.getPositions(), tmpID));
        positionsTableView.requestFocus();
    }

    @FXML
    void cancelButtonClick(ActionEvent event) {
        positionsTableView.getItems().clear();
        positionsTableView.getItems().addAll(tmpList);
        allControlsSetEditable(false);
        setFieldsValues(selectedRecord);
        positionsTableView.getSelectionModel().select(selectedRecord);
        positionsTableView.requestFocus();
    }

    @FXML
    void deleteButtonClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Внимание!");
        alert.setHeaderText("Вы уверены, что хотите удалить запись: ");
        alert.setContentText("Запись: <" + selectedRecord.getDescription() + "> ?");
        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            AppData.getPositions().remove(selectedRecord);
            AppData.getDb().markRecordAsDeleted("employee_positions",
                    "position_isdeleted",
                    "position_id",
                    selectedRecord.getID());
            fillTableView();
            positionsTableView.getSelectionModel().select(0);
            selectedRecord = positionsTableView.getItems().get(0);
            setFieldsValues(selectedRecord);
            positionsTableView.requestFocus();
        }

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


