package forms.Settings;

public abstract class SettingsPaneController{

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