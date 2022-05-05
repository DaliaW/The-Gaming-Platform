package guc.bttsBtngan.notification.controller;

import guc.bttsBtngan.notification.entity.Notifications;
import guc.bttsBtngan.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/notifications")
    public String saveNotification(@RequestBody Notifications notification){
        try {
            return notificationService.createNotification(notification);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PutMapping("/notifications/{notificationID}")
    public String updateNotification(@PathVariable String notificationID,@RequestBody Notifications notification) {
        try {
            return notificationService.updateNotification(notificationID,notification);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @DeleteMapping("/notifications/{notificationID}/{userID}")
    public String deleteNotification(@PathVariable String notificationID,@PathVariable String userID) {
        try {
            return notificationService.deleteNotification(notificationID,userID);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @GetMapping("/notifications/{userID}")
    public ArrayList getNotification(@PathVariable String userID) {
        try {
            return notificationService.getNotification(userID);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
