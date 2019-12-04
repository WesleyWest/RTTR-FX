package GUI;

import GUI.Settings.SettingsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import objects.BL.AppData;
import objects.GUI.GUIData;

import java.io.IOException;

public abstract class GUIController extends AppData {
    private SettingsController settingsController;
    public abstract void setNewTheme();
    public abstract void restartApp();

    public void restart(Stage stageToClose) {
        String parentControllerName = settingsController.getParentController().getClass().getSimpleName();
        if (!parentControllerName.equals("LoginController")) {
            getDb().close();
        }
        stageToClose.close();
        setUsers(null);
        GUIData newApp = new GUIData();
        try {
            newApp.startApp(GUIData.getPrimaryStage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent initializeNewSettingsWindow() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("../Settings/SettingsWindow.fxml").openStream());
        settingsController = loader.getController();
        settingsController.setParentController(this);
        return root;
    }

}
