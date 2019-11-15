package objects.BL.Employees;

import objects.BL.SimpleObject;

public class Division extends SimpleObject {

    private String code;

    public Division(Integer id, String description, String code) {
        super(id, description);
        this.code=code;
    }




}
