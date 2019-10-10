package objects.Employees;

public class Position {
    private Integer id;
    private String position;

    public Position(Integer id, String position) {
        this.id = id;
        this.position = position;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
