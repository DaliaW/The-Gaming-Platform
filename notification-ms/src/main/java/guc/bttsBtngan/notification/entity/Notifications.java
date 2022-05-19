package guc.bttsBtngan.notification.entity;

import java.util.Arrays;
import java.util.List;

public class Notifications {

    private String type;
    private List<String> userIDs;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Notifications{" +
                "type='" + type + '\'' +
                ", userIDs=" + userIDs +
                '}';
    }

    public List<String> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(List<String> userIDs) {
        this.userIDs = userIDs;
    }
}
