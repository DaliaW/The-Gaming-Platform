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
    public String saveNotification(@RequestBody Notifications notification)throws ExecutionException {
        return notificationService.createNotification(notification);
    }

    @PutMapping("/notifications/{notificationID}")
    public String updateNotification(@PathVariable String notificationID,@RequestBody Notifications notification)throws ExecutionException {
        return notificationService.updateNotification(notificationID,notification);
    }
    @DeleteMapping("/notifications/{notificationID}/{userID}")
    public String deleteNotification(@PathVariable String notificationID,@PathVariable String userID) throws ExecutionException, InterruptedException {
        return notificationService.deleteNotification(notificationID,userID);
    }

//    @GetMapping("/notifications/{name}")
//    public Notifications getNotification(@PathVariable String name) throws ExecutionException, InterruptedException {
//        //  System.out.println("\n\nhi1\n\n");
//        return notificationService.getNotification(name);
//    }


    @GetMapping("/notifications/{userID}")
    public ArrayList getNotification(@PathVariable String userID) throws ExecutionException, InterruptedException {
        return notificationService.getNotification(userID);
    }
}
