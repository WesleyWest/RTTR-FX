package objects.Employees;

import objects.SimpleObject;

public class Division extends SimpleObject {

    private String code;

    public Division(Integer id, String description, String code) {
        super(id, description);
        this.code=code;
    }




}
