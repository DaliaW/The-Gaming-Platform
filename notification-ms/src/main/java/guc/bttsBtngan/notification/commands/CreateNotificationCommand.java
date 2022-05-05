package guc.bttsBtngan.notification.commands;

import guc.bttsBtngan.notification.entity.Notifications;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class CreateNotificationCommand extends NotificationCommand {

    public Object execute(HashMap<String, Object> map) throws Exception{
        Notifications notifications = new Notifications();
        notifications.setType((String) map.get("type"));
        notifications.setUserIDs((List<String>) map.get("userIDs"));
        return getService().createNotification(notifications);
    }
}
