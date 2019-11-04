import objects.AppData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        AppData.writeDefaultSettingsToFile();
//        System.exit(0);

        AppData.readSettingsFromFile();
        System.exit(0);
        Parent root = FXMLLoader.load(getClass().getResource("forms/Login/LoginWindow.fxml"));

        primaryStage.setTitle("RTTR-Master");
        primaryStage.getIcons().add(new Image("resources/main.png"));
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 465, 328);
        scene.getStylesheets().add(AppData.getPathCSS());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


}
