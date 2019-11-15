package objects.GUI;

import java.util.ArrayList;

public class ColorTheme {
    private String name;
    private String path;

    public ColorTheme(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public static String getPathByName(ArrayList<ColorTheme> themes, String name){
        for (ColorTheme theme : themes) {
            if (theme.getName().equals(name)) return theme.getPath();
        }
        return "";
    }
    @Override
    public String toString() {
        return "ColorTheme{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
