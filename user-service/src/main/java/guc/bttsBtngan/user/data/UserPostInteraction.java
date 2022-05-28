package guc.bttsBtngan.user.data;

import io.swagger.annotations.ApiModel;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.util.List;

@ApiModel(description = "model for user interaction but in MongoDB")
@Document
public class UserPostInteraction {
    @Id
    private String userId;
    private List<String> followers;
    private List<String> following;
    private List<String> blockedBy;
    private List<UserReports> userReports;

    public UserPostInteraction() {
    }

    public UserPostInteraction(String userId, List<String> followers, List<String> following, List<String> blockedBy , List<UserReports> userReports) {
        super();
        this.userId = userId;
        this.followers = followers;
        this.following = following;
        this.blockedBy = blockedBy;
        this.userReports = userReports;
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

    public List<UserReports> getUserReports() {
        return userReports;
    }

    public void setUserReports(List<UserReports> userReports) {
        this.userReports = userReports;
    }

    @Override
    public String toString() {
        return "UserPostInteraction [userId=" + userId + ", followers=" + followers + ", following=" + following
                + ", blockedBy=" + blockedBy + "]";
    }

}
