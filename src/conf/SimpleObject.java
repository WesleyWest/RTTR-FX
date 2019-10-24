package conf;

public class SimpleObject<T> extends AbstractObject {
    private Integer id;
    String description;

    public SimpleObject(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getID() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getID()+": "+getDescription();
    }
}