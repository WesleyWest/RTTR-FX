package objects;

import objects.Technic.Technic;

import java.sql.Timestamp;

public class Request {
    private Integer ID;
    private Technic technic;
    private Timestamp openDate;
    private Timestamp closeDate;
    private String problemDescription;
    private String decisionDescription;
    private boolean status;

    public Request(Integer ID, Technic technic, Timestamp openDate, Timestamp closeDate, String problemDescription, String decisionDescription, boolean status) {
        this.ID = ID;
        this.technic = technic;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.problemDescription = problemDescription;
        this.decisionDescription = decisionDescription;
        this.status = status;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTechnic() {
        return technic.getName();
    }

    public void setTechnic(Technic technic) {
        this.technic = technic;
    }

    public String getOpenDate() {
        String openDateStr=openDate.toString();
        return openDateStr.substring(0,openDateStr.length()-5);
    }

    public void setOpenDate(Timestamp openDate) {
        this.openDate = openDate;
    }

    public Timestamp getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Timestamp closeDate) {
        this.closeDate = closeDate;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
