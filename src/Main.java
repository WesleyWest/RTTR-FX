import conf.AppData;
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
//        private static String dbHost="localhost";
//        private static String dbPort="3306";
//        private static String dbUser="root";
//        private static String dbPass="diamond";
//        private static String dbSchema ="rttr-base";
//        private static String pathCSS="";

//        AppData.writeDefaultSettingsToFile();
//        System.exit(0);

        AppData.readSettingsFromFile();
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
