package forms.Settings.DBSettingsPanes;

import forms.Settings.SettingsController;

public abstract class DBSettingsPaneController {

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
}
