package objects.Technic;

import conf.ObjectInterface;
import objects.Employees.Employee;

public class Technic implements ObjectInterface {
    private Integer id;
    private TechnicStatus status;
    private TechnicType type;
    private Employee owner;
    private Employee repairer;

    public Technic(Integer id, TechnicStatus status, TechnicType type, Employee owner, Employee repairer) {
        this.id = id;
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

    public TechnicStatus getStatus() {
        return status;
    }

    public void setStatus(TechnicStatus status) {
        this.status = status;
    }

    public TechnicType getType() {
        return type;
    }

    public void setType(TechnicType type) {
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
