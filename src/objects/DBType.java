package objects;

public class DBType {
    String name;
    String className;
    String pathToFXML;
    String paneControllerClassName;

    public DBType(String name, String className, String pathToFXML, String paneControllerClassName) {
        this.name = name;
        this.className = className;
        this.pathToFXML = pathToFXML;
        this.paneControllerClassName = paneControllerClassName;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public String getPathToFXML() {
        return pathToFXML;
    }

    public void setPathToFXML(String pathToFXML) {
        this.pathToFXML = pathToFXML;
    }

    public String getPaneControllerClassName() {
        return paneControllerClassName;
    }

    public void setPaneControllerClassName(String paneControllerClassName) {
        this.paneControllerClassName = paneControllerClassName;
    }

    @Override
    public String toString() {
        return "DBType{" +
                "name='" + name + '\'' +
                ", className='" + className + '\'' +
                ", pathToFXML='" + pathToFXML + '\'' +
                ", paneControllerClassName='" + paneControllerClassName + '\'' +
                '}'+'\n';
    }
}
