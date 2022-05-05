package guc.bttsBtngan.notification.commands;

import java.util.ArrayList;
import java.util.HashMap;

public class GetNotificationCommand extends NotificationCommand{
    public Object execute(HashMap<String, Object> map) throws Exception{
        return getService().getNotification((String) map.get("userID"));
    }
}
