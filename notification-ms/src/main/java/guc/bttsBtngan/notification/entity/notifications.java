package guc.bttsBtngan.notification.entity;

import java.util.Arrays;

public class notifications {

    private String type;
    private String[] userIDs;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "notifications{" +
                "type='" + type + '\'' +
                ", userIDs=" + Arrays.toString(userIDs) +
                '}';
    }

    public String[] getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(String[] userIDs) {
        this.userIDs = userIDs;
    }
}
