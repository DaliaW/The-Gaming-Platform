package guc.bttsBtngan.user.data;

import java.util.List;

public class UserPostInteraction {
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
}