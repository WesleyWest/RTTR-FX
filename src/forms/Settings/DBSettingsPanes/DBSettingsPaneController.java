package forms.Settings.DBSettingsPanes;

import forms.Settings.SettingsController;

public interface DBSettingsPaneController {
    public void setInformation();
    public void getInformation();
    public int getDataHash();
    public void setParentController(SettingsController parentController);
}
