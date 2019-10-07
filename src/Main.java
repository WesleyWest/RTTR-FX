import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

            Parent root = FXMLLoader.load(getClass().getResource("windows_fxml/LoginWindow.fxml"));
            primaryStage.setTitle("RTTR-Master");
            primaryStage.setResizable(false);
            primaryStage.setScene(new Scene(root, 465, 328));
            primaryStage.show();
        }


    }
