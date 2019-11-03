package forms.Settings;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class DBSettingsPaneFactory {

    public Pane getPaneByDBType(String type){

        try {
            return (Pane) FXMLLoader.load(getClass().getResource("SettingsPaneMySQL.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
