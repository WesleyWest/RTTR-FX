package objects.BL;

public class SimpleObject<T> implements StandardBehavior {
    private Integer id;
    String description;
    boolean isDeleted;

    public SimpleObject(Integer id, String description, boolean isDeleted) {
        this.id = id;
        this.description = description;
        this.isDeleted = isDeleted;
    }

    public Integer getID() {
        return id;
    }

    @Override
    public boolean isDeleted() {
        return isDeleted;
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
