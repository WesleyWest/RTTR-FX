package GUI.Settings;

import javafx.scene.control.Label;

public abstract class SettingsPaneController {
    private SettingsController parentController;

    public abstract void setInformation();
    public abstract void saveInformationToIni();
    public abstract int getDataHash();

    public void setParentController(SettingsController parentController) {
        this.parentController = parentController;
    }

    public SettingsController getParentController() {
        return parentController;
    }

    public void setModeLabelState(Label label, boolean state) {
        if (state) {
            label.setText("Режим редактирования");
            label.getStyleClass().set(0,"label-edit-mode");
        } else {
            label.setText("Режим просмотра");
            label.getStyleClass().set(0,"label-view-mode");
        }
    }

}
