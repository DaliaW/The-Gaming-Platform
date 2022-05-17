package guc.bttsBtngan.notification.commands;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class GetNotificationCommand extends NotificationCommand{
    public Object execute(HashMap<String, Object> map) throws Exception{
        return getService().getNotification((String) map.get("userID"));
    }
}
