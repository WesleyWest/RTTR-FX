package objects.BL.Employees;

import objects.BL.SimpleObject;

public class Division extends SimpleObject {

    private String code;
    private boolean isDeleted;

    public Division(Integer id, String code, String description, boolean isDeleted) {
        super(id, description);
        this.code=code;
        this.isDeleted=isDeleted;
    }

    public String getCode() {
        return code;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    @Override
    public String toString() {
        return "Division{" +
                "id='"+ getID()+'\''+
                "description='"+ getDescription()+'\''+
                "code='" + code + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
