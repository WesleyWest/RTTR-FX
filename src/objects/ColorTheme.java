package objects;

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

    @Override
    public String toString() {
        return "ColorTheme{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
