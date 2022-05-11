package guc.bttsBtngan.user.data;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@Document
public class UserPostInteraction {
    @Id
    private String userId;
    private List<String> followers;
    private List<String> following;
    private List<String> blockedBy;

    public UserPostInteraction() {
    }

    public UserPostInteraction(String userId, List<String> followers, List<String> following, List<String> blockedBy) {
        super();
        this.userId = userId;
        this.followers = followers;
        this.following = following;
        this.blockedBy = blockedBy;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
    }

    public List<String> getBlockedBy() {
        return blockedBy;
    }

    public void setBlockedBy(List<String> blockedBy) {
        this.blockedBy = blockedBy;
    }

    @Override
    public String toString() {
        return "UserPostInteraction [userId=" + userId + ", followers=" + followers + ", following=" + following
                + ", blockedBy=" + blockedBy + "]";
    }

    public static class UserReports {
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

}
