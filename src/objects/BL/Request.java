package objects.BL;

import objects.BL.Technic.Technic;
import objects.BL.Users.User;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Request {
    private Integer ID;
    private Technic technic;
    private Timestamp openDate;
    private Timestamp closeDate;
    private String problemDescription;
    private String decisionDescription;
    private boolean status;
    private User repairer;
    private User author;
    private User closer;
    private String changeHistory;

    public Request(Integer ID, Technic technic, Timestamp openDate, Timestamp closeDate, String problemDescription, String decisionDescription, boolean status, User repairer, User author, User closer, String changeHistory) {
        this.ID = ID;
        this.technic = technic;
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.problemDescription = problemDescription;
        this.decisionDescription = decisionDescription;
        this.status = status;
        this.repairer = repairer;
        this.author = author;
        this.closer = closer;
        this.changeHistory = changeHistory;
    }

    public String getChangeHistory() {
        return changeHistory;
    }

    public void setChangeHistory(String changeHistory) {
        this.changeHistory = changeHistory;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTechnic() {
        return technic.getType().getDescription() + " " + technic.getName();
    }

    public Technic getTechnicAsObject() {
        return technic;
    }

    public void setTechnic(Technic technic) {
        this.technic = technic;
    }

    public String getOpenDateAsString() {
        String openDateStr = openDate.toString();
        return openDateStr.substring(0, openDateStr.length() - 5);
    }

    public Timestamp getOpenDateTime() {
        return openDate;
    }

    public void setOpenDate(Timestamp openDate) {
        this.openDate = openDate;
    }

    public String getCloseDateTime() {
        if (closeDate != null) {
            String closeDateStr = closeDate.toString();
            return closeDateStr.substring(0, closeDateStr.length() - 5);
        } else {
            return "";
        }
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
        if (decisionDescription != null) {
            return decisionDescription;
        } else {
            return "";
        }
    }

    public void setDecisionDescription(String decisionDescription) {
        this.decisionDescription = decisionDescription;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getCloser() {
        return closer;
    }

    public void setCloser(User closer) {
        this.closer = closer;
    }

    public User getRepairer() {
        return repairer;
    }

    public void setRepairer(User repairer) {
        this.repairer = repairer;
    }

    @Override
    public String toString() {
        return "Request{" +
                "ID=" + ID +
                ", technic=" + technic +
                ", openDate=" + openDate +
                ", closeDate=" + closeDate +
                ", problemDescription='" + problemDescription + '\'' +
                ", decisionDescription='" + decisionDescription + '\'' +
                ", status=" + status +
                ", author=" + author +
                ", closer=" + closer +
                '}';
    }
}
