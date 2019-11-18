import javafx.application.Application;
import javafx.stage.Stage;
import objects.GUI.GUIData;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        AppData.writeDefaultSettingsToFile();
//        System.exit(0);
        GUIData app = new GUIData();
        GUIData.setPrimaryStage(primaryStage);
        app.startApp(primaryStage);
    }




}
