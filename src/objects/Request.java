package objects;

import objects.Technic.Technic;

import java.util.Date;

public class Request {
    private Integer ID;
    private Technic technic;
    private Date OpenDate;
    private Date CloseDate;
    private String problemDescription;
    private String decisionDescription;

    public Request(Integer ID, Technic technic, Date openDate, Date closeDate, String problemDescription, String decisionDescription) {
        this.ID = ID;
        this.technic = technic;
        OpenDate = openDate;
        CloseDate = closeDate;
        this.problemDescription = problemDescription;
        this.decisionDescription = decisionDescription;
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

    public Date getOpenDate() {
        return OpenDate;
    }

    public void setOpenDate(Date openDate) {
        OpenDate = openDate;
    }

    public Date getCloseDate() {
        return CloseDate;
    }

    public void setCloseDate(Date closeDate) {
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
