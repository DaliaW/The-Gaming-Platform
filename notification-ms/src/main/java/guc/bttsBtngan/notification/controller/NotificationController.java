package guc.bttsBtngan.notification.controller;

import guc.bttsBtngan.notification.entity.Notifications;
import guc.bttsBtngan.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/notifications")
    public String saveNotification(@RequestBody Notifications notification)throws ExecutionException {
        System.out.println("\n\nhi1\n\n");
        return notificationService.saveNotification(notification);
    }
}
