package conf;

public class TechnicInterface implements ObjectInterface {
    private Integer id;
    String description;

    public TechnicInterface(Integer id, String description) {
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
}
