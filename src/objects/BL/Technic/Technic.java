package objects.BL.Technic;

import objects.BL.idReturnable;
import objects.BL.SimpleObject;
import objects.BL.Employees.Employee;

public class Technic implements idReturnable {
    private Integer id;
    private String name;
    private String details;
    private SimpleObject<TechnicStatus> status;
    private SimpleObject<TechnicType> type;
    private Employee owner;
    private Employee repairer;

    public Technic(Integer id, String name, String details, SimpleObject<TechnicStatus> status, SimpleObject<TechnicType> type, Employee owner, Employee repairer) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.status = status;
        this.type = type;
        this.owner = owner;
        this.repairer = repairer;
    }



    public Integer getID() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public SimpleObject<TechnicStatus> getStatus() {
        return status;
    }

    public void setStatus(SimpleObject<TechnicStatus> status) {
        this.status = status;
    }

    public SimpleObject<TechnicType> getType() {
        return type;
    }

    public void setType(SimpleObject<TechnicType> type) {
        this.type = type;
    }

    public Employee getOwner() {
        return owner;
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }

    public Employee getRepairer() {
        return repairer;
    }

    public void setRepairer(Employee repairer) {
        this.repairer = repairer;
    }
}





