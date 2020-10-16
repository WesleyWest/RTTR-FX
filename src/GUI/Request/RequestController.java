package GUI.Request;

import GUI.Main.MainController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import objects.BL.AppData;
import objects.BL.Employees.Employee;
import objects.BL.Request;
import objects.BL.Technic.Technic;

public class RequestController {


    @FXML
    private Button exitButton;
    @FXML
    private Button applyButton;
    @FXML
    private Button OKButton;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private AnchorPane handleRequestAnchorPane;
    @FXML
    private AnchorPane headerAnchorPane;
    @FXML
    private Label headerLabelBig;
    @FXML
    private Label headerLabelSmall;
    @FXML
    private CheckBox closeRequestCheckBox;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField requestOpenTimeField;

    @FXML
    private TextField ownerTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField problemDescriptionTextField;

    @FXML
    private TextField requestCloseTimeField;

    @FXML
    private TextField worksDescriptionTextField;

    @FXML
    private ComboBox<Technic> technicComboBox;

    @FXML
    private ComboBox<Employee> ownerComboBox;

    @FXML
    private ComboBox<Employee> repairerComboBox;

    private MainController parentController;

    private static boolean isEditingMode=false;

    private Request activeRequest;

    public void setParentController(MainController parentController) {
        this.parentController = parentController;
    }

    public MainController getParentController() {
        return parentController;
    }

    @FXML
    void initialize() {
        applyCSS();
        handleRequestAnchorPane.setVisible(false);
    }

    void applyCSS() {
        headerLabelBig.getStyleClass().set(0, "label-header-big");
        headerLabelSmall.getStyleClass().set(0, "label-header-small");
        headerAnchorPane.getStyleClass().add("anchor-pane-header");
        mainAnchorPane.getStyleClass().add("anchor-pane-main");
    }

    private void fillComboBoxes(){
        System.out.println(AppData.getTechnic());
        technicComboBox.setItems(AppData.getTechnic());
    }

    private void fillFieldsByMode() {
        if (isEditingMode) {
            activeRequest = parentController.getActiveRequest();
            idTextField.setText(activeRequest.getID().toString());
            authorTextField.setText(activeRequest.getAuthor().getEmployee().getShortDescription());
            requestOpenTimeField.setText(activeRequest.getOpenDate());
            fillComboBoxes();
        } else {

        }

    }

    private void setApplyButtonByMode() {
        if (isEditingMode) {
            applyButton.setText("Применить");
        } else {
            applyButton.setText("Добавить");
        }
    }

    @FXML
    void okButtonClick(ActionEvent event) {

    }

    @FXML
    void closeRequestCheckBoxFire() {
        if (closeRequestCheckBox.isSelected()) {
            applyButton.setText("Завершить");
            handleRequestAnchorPane.setVisible(true);
        } else {
            setApplyButtonByMode();
            handleRequestAnchorPane.setVisible(false);
        }

    }

    @FXML
    void exitButtonClick(ActionEvent event) {
        Stage oldStage = (Stage) exitButton.getScene().getWindow();
        oldStage.close();
    }

    @FXML
    void applyButtonClick(ActionEvent event) {

    }

    public void setData(boolean isEditing){
        isEditingMode=isEditing;
        setApplyButtonByMode();
        fillFieldsByMode();

    }
/**
 * Main Settings section
 */

    /*public GUIController getParentController() {
        return parentController;
    }

    public void setParentController(GUIController parentController) {
        this.parentController = parentController;
    }*/
}
