package objects.BL.Technic;

import objects.BL.StandardBehavior;
import objects.BL.SimpleObject;
import objects.BL.Employees.Employee;

public class Technic implements StandardBehavior {
    private Integer id;
    private String name;
    private String description;
    private SimpleObject<TechnicStatus> status;
    private SimpleObject<TechnicType> type;
    private Employee owner;

    public Technic(Integer id, String name, String description, SimpleObject<TechnicStatus> status, SimpleObject<TechnicType> type, Employee owner) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.type = type;
        this.owner = owner;
    }

    public Integer getID() {
        return id;
    }

    @Override
    public boolean isDeleted() {
        return false;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SimpleObject<TechnicStatus> getStatus() {
        return status;
    }
    public String getStringStatus(){
        return this.status.getDescription();
    }

    public void setStatus(SimpleObject<TechnicStatus> status) {
        this.status = status;
    }

    public SimpleObject<TechnicType> getType() {
        return type;
    }

    public String getStringType(){
        return this.type.getDescription();
    }

    public void setType(SimpleObject<TechnicType> type) {
        this.type = type;
    }

    public Employee getOwner() {
        return owner;
    }

    public String getStringOwner(){
        return this.owner.getShortDescription();
    }

    public void setOwner(Employee owner) {
        this.owner = owner;
    }
}





