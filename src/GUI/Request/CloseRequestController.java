package GUI.Request;

import GUI.Main.MainController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.BL.AppData;
import objects.BL.Employees.Employee;
import objects.BL.Request;
import objects.BL.Technic.Technic;
import objects.BL.Users.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class CloseRequestController {


    @FXML
    private Button exitButton;
    @FXML
    private Button applyButton;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private AnchorPane headerAnchorPane;
    @FXML
    private Label headerLabelBig;
    @FXML
    private Label headerLabelSmall;
    @FXML
    private TextField closerTextField;
    @FXML
    private Button setCloseTimeToNowButton;
    @FXML
    private TextField requestCloseTimeHoursField;
    @FXML
    private TextField requestCloseTimeMinutesField;
    @FXML
    private DatePicker requestCloseDatePicker;
    @FXML
    private TextField worksDescriptionTextField;

    private MainController parentController;
    private static boolean isEditingMode = false;
    private Request activeRequest;
    private int referenceDataHash;

    public void setParentController(MainController parentController) {
        this.parentController = parentController;
    }

    public MainController getParentController() {
        return parentController;
    }

    private ArrayList<Technic> allTechnic = new ArrayList<>();

    int calculateCurrentDataHash() {

        int tmpHash = 0;
        try {
            tmpHash = closerTextField.getText().hashCode()
                    + requestCloseDatePicker.getValue().hashCode()
                    + requestCloseTimeHoursField.getText().hashCode()
                    + requestCloseTimeMinutesField.getText().hashCode()
                    + worksDescriptionTextField.getText().hashCode();
        } catch (Exception e) {

        }

        return tmpHash;
    }

    @FXML
    void checkChanges() {
        if (referenceDataHash != calculateCurrentDataHash()) {
            applyButton.setDisable(false);
        } else {
            applyButton.setDisable(true);
        }
    }

    @FXML
    void initialize() {
        applyCSS();
        closerTextField.setText(AppData.getUser().getEmployee().getShortDescription());
        setTimeToNow(new ActionEvent());

    }

    void applyCSS() {
        headerLabelBig.getStyleClass().set(0, "label-header-big");
        headerLabelSmall.getStyleClass().set(0, "label-header-small");
        headerAnchorPane.getStyleClass().add("anchor-pane-header");
        mainAnchorPane.getStyleClass().add("anchor-pane-main");
    }


    private void setApplyButtonByMode() {
        if (isEditingMode) {
            applyButton.setText("Применить");
        } else {
            applyButton.setText("Добавить");
        }
    }

    @FXML
    void setTimeToNow(ActionEvent event) {
        LocalDateTime now = LocalDateTime.now();
        requestCloseDatePicker.setValue(now.toLocalDate());
        requestCloseTimeHoursField.setText(String.valueOf(now.getHour()));
        requestCloseTimeMinutesField.setText(String.valueOf(now.getMinute()));

//        checkChanges();
    }


    @FXML
    void exitButtonClick(ActionEvent event) {
        Stage oldStage = (Stage) exitButton.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    void applyButtonClick(ActionEvent event) {

    }

}
