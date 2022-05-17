package guc.bttsBtngan.notification.commands;

import guc.bttsBtngan.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class NotificationCommand extends Command{

    private NotificationService service;

    public NotificationService getService() {
        return service;
    }

    @Autowired
    public final void setService(NotificationService service) {
        this.service = service;
    }
}
