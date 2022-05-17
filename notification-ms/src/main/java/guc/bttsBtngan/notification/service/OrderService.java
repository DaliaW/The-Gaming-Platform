package guc.bttsBtngan.notification.service;


import guc.bttsBtngan.notification.entity.Notifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

@Service
public class OrderService {
    @Autowired
    NotificationService notificationService ;

    public OrderService( NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    public ArrayList getNotification(String userID) throws ExecutionException, InterruptedException {
        return notificationService.getNotification(userID);
    }
    public String deleteNotification(String notificationID,String userID) throws Exception {
        return notificationService.deleteNotification(notificationID,userID);
    }

    public String updateNotification(String notificationID, Notifications notification) throws Exception {
        return notificationService.updateNotification(notificationID,notification);
    }

    public String createNotification(Notifications notification) throws ExecutionException, InterruptedException {
        return notificationService.createNotification(notification);
    }


    }
