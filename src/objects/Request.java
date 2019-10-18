package objects;

import objects.Technic.Technic;

import java.sql.Timestamp;

public class Request {
    private Integer ID;
    private Technic technic;
    private Timestamp OpenDate;
    private Timestamp CloseDate;
    private String problemDescription;
    private String decisionDescription;

    public Request(Integer ID, Technic technic, Timestamp openDate) {
        this.ID = ID;
        this.technic = technic;
        OpenDate = openDate;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Technic getTechnic() {
        return technic;
    }

    public void setTechnic(Technic technic) {
        this.technic = technic;
    }

    public Timestamp getOpenDate() {
        return OpenDate;
    }

    public void setOpenDate(Timestamp openDate) {
        OpenDate = openDate;
    }

    public Timestamp getCloseDate() {
        return CloseDate;
    }

    public void setCloseDate(Timestamp closeDate) {
        CloseDate = closeDate;
    }

    public String getProblemDescription() {
        return problemDescription;
    }

    public void setProblemDescription(String problemDescription) {
        this.problemDescription = problemDescription;
    }

    public String getDecisionDescription() {
        return decisionDescription;
    }

    public void setDecisionDescription(String decisionDescription) {
        this.decisionDescription = decisionDescription;
    }
}
