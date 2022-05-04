package guc.bttsBtngan.notification.commands;

import guc.bttsBtngan.notification.entity.Notifications;

import java.util.HashMap;
import java.util.List;

public class UpdateNotificationCommand extends NotificationCommand{
    public void execute(HashMap<String, Object> map) {
        Notifications notifications = new Notifications();
        notifications.setType((String) map.get("type"));
        notifications.setUserIDs((List<String>) map.get("userIDs"));
        try {
            String res = getService().updateNotification((String) map.get("notificationID"),notifications);
            //TODO: message queues
        } catch (Exception  e) {
            //TODO:
        }
    }
}
