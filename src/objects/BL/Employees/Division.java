package objects.BL.Employees;

import objects.BL.SimpleObject;
public class Division extends SimpleObject  {

    private String code;

    public Division(Integer id, String code, String description, boolean isDeleted) {
        super(id, description, isDeleted);
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.getCode();
    }


}
