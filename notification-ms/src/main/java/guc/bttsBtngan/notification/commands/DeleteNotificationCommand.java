package guc.bttsBtngan.notification.commands;

import java.util.HashMap;

public class DeleteNotificationCommand extends NotificationCommand{
    public void execute(HashMap<String, Object> map) {
        try {
            String res = getService().deleteNotification((String) map.get("notificationID"), (String) map.get("userID"));
        } catch(Exception e) {

        }
    }
}
