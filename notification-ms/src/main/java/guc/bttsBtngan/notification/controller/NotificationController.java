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
      //  System.out.println("\n\nhi1\n\n");
        return notificationService.createNotification(notification);
    }

//    @GetMapping("/notifications/{name}")
//    public Notifications getNotification(@PathVariable String name) throws ExecutionException, InterruptedException {
//        //  System.out.println("\n\nhi1\n\n");
//        return notificationService.getNotification(name);
//    }


    @GetMapping("/notifications/{userID}")
    public ArrayList getNotification(@PathVariable String userID) throws ExecutionException, InterruptedException {
        //  System.out.println("\n\nhi1\n\n");
        return notificationService.getNotification(userID);
    }
}
