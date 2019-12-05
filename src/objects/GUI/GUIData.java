package objects.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import objects.DB.DBType;
import org.ini4j.Wini;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;

public class GUIData {
    private static String settingsFilePath = "src/conf/settings.ini";
    private static Window owner;
    private static Stage primaryStage;
    private static String themeName;
    private static String pathCSS;
    private static ArrayList<ColorTheme> themes = new ArrayList<>();
    private static ArrayList<DBType> dbTypes = new ArrayList<>();
    private static String activeSQLDataBaseType;
    private static Wini iniFile;
    private static String settingsWindowCaller;

    public static String getSettingsFilePath() {
        return settingsFilePath;
    }

    public static void setSettingsFilePath(String settingsFilePath) {
        GUIData.settingsFilePath = settingsFilePath;
    }

    public static String getSettingsWindowCaller() {
        return settingsWindowCaller;
    }

    public static void setSettingsWindowCaller(String settingsWindowCaller) {
        GUIData.settingsWindowCaller = settingsWindowCaller;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage primaryStage) {
        GUIData.primaryStage = primaryStage;
    }

    public static Wini getIniFile() {
        return iniFile;
    }

    public static ArrayList<ColorTheme> getThemes() {
        return themes;
    }

    public static void setThemes(ArrayList<ColorTheme> themes) {
        GUIData.themes = themes;
    }

    public static ArrayList<DBType> getDbTypes() {
        return dbTypes;
    }

    public static void setDbTypes(ArrayList<DBType> dbTypes) {
        GUIData.dbTypes = dbTypes;
    }

    public static String getThemeName() {
        return themeName;
    }

    public static void setThemeName(String themeName) {
        GUIData.themeName = themeName;
    }

    public static String getPathCSS() {
        return pathCSS;
    }

    public static void setPathCSS(String pathCSS) {
        GUIData.pathCSS = pathCSS;
    }

    public static String getActiveSQLDataBaseType() {
        return activeSQLDataBaseType;
    }

    public static void setActiveSQLDataBaseType(String activeSQLDataBaseType) {
        GUIData.activeSQLDataBaseType = activeSQLDataBaseType;
    }

    public static Window getOwner() {
        return owner;
    }

    public static void setOwner(Window owner) {
        GUIData.owner = owner;
    }

    public static void showAlert(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("resources/main.png"));
        alert.setTitle("RTTR-Master");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void openCustomWindow(ActionEvent event, Parent root, int width, int height, Modality modality, boolean isWindowResizable) {
        Stage stage = new Stage();
        stage.setTitle("RTTR-Master (Requests to Technics Repair)");
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(isWindowResizable);
        stage.getIcons().add(new Image("resources/main.png"));
        Scene scene = new Scene(root, width, height);
        scene.getStylesheets().add(getPathCSS());
        stage.setScene(scene);
        stage.initModality(modality);
        stage.initOwner(getOwner());
        stage.show();
    }

    public static void readSettingsFromFile() {
        try {
            iniFile = new Wini(new File(String.valueOf(Paths.get(settingsFilePath))));
            setThemeName(iniFile.get("MAIN","Active theme"));
            themes.clear();
            for (String key : iniFile.get("THEMES").keySet()) {
                themes.add(new ColorTheme(key,iniFile.get("THEMES",key)));
                if (key.equals(getThemeName())){
                    setPathCSS(iniFile.get("THEMES",key));
                }
            }
            dbTypes.clear();
            for (String key : iniFile.get("DBTYPES").keySet()) {
                String name = key;
                String className= iniFile.get("DBTYPES",key);
                String pathToFXML = iniFile.get(name.toUpperCase(),"Path to FXML");
                String paneControllerClassName = iniFile.get(name.toUpperCase(),"PaneController ClassName");
                DBType type = new DBType(name,className,pathToFXML,paneControllerClassName);
                dbTypes.add(type);
            }
            setActiveSQLDataBaseType(iniFile.get("MAIN","Active DB type"));

            System.out.println("File (" + new File(settingsFilePath).getAbsolutePath() + ") was loaded successfully.");
        } catch (Exception e) {
            System.out.println("Something wrong with the <" + settingsFilePath + ">: " + e.getMessage());
        }

    }

    public static void writeDefaultSettingsToFile() {
        try {
            Wini iniFile = new Wini(new File(String.valueOf(Paths.get(settingsFilePath))));
            iniFile.clear();
            iniFile.add("THEMES", "Blue theme", new File(String.valueOf(Paths.get("src/themes/BlueTheme.css"))));
            iniFile.add("THEMES", "Dark theme", new File(String.valueOf(Paths.get("src/themes/DarkTheme.css"))));
            iniFile.add("MAIN", "Active DB type", "SQLITE");
            iniFile.add("MAIN", "Active theme", "Blue Theme");
            iniFile.add("SQLITE", "Path to file", new File(String.valueOf(Paths.get("src/db/rttr-base.db"))).getAbsolutePath());
            iniFile.add("MYSQL", "Host", "localhost");
            iniFile.add("MYSQL", "Port", "3306");
            iniFile.add("MYSQL", "Schema", "rttr-base");
            iniFile.add("MYSQL", "Login", "root");
            iniFile.add("MYSQL", "Password", "diamond");
            iniFile.store();
            System.out.println("The file was successfully saved.");
        } catch (Exception e) {
            System.out.println("File can't be opened. Program terminates.");
            e.printStackTrace();
        }
    }

    public void startApp(Stage primaryStage) throws java.io.IOException {
        readSettingsFromFile();
        Parent root = FXMLLoader.load(getClass().getResource("../../GUI/Login/LoginWindow.fxml"));
        primaryStage.setTitle("RTTR-Master");
        primaryStage.getIcons().add(new Image("resources/main.png"));
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, 465, 328);
        scene.getStylesheets().add(getPathCSS());
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static boolean isNotEmpty(ArrayList<String> args){
        for (String arg : args) {
            if(arg.length()==0){
                return false;
            }
        }
        return true;
    }
}
