package guc.bttsBtngan.post.data;

public class UserReports {
    private String issuerId;
    private String comment;

    public UserReports() {
    }

    public UserReports(String issuerId, String comment) {
        super();
        this.issuerId = issuerId;
        this.comment = comment;
    }

    public String getIssuerId() {
        return issuerId;
    }

    public void setIssuerId(String issuerId) {
        this.issuerId = issuerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "UserReports{" +
                "issuerId='" + issuerId + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
