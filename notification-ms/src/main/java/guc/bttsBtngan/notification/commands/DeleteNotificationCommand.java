package guc.bttsBtngan.notification.commands;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DeleteNotificationCommand extends NotificationCommand{
    public Object execute(HashMap<String, Object> map) throws Exception{
        return getService().deleteNotification((String) map.get("notificationID"), (String) map.get("userID"));
    }
}
