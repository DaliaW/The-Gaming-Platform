package guc.bttsBtngan.notification.commands;

import java.util.ArrayList;
import java.util.HashMap;

public class GetNotificationCommand extends NotificationCommand{
    public void execute(HashMap<String, Object> map) {
        try {
            ArrayList res = getService().getNotification((String) map.get("userID"));
        } catch(Exception e) {

        }
    }
}
