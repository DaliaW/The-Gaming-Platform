package test;

import guc.bttsBtngan.notification.NotificationMain;
import guc.bttsBtngan.notification.entity.Notifications;
import guc.bttsBtngan.notification.service.NotificationService;
import guc.bttsBtngan.notification.service.OrderService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@SpringBootTest(classes = NotificationMain.class)
@ActiveProfiles("test") //@ActiveProfiles("guc/bttsBtngan/notification/test")
@RunWith(SpringJUnit4ClassRunner.class)

public class NotificationTests {
    @Autowired
    private OrderService orderService;

    @Autowired
    private NotificationService notificationService;

  //  @Test
//    public void whenUserIdIsProvided_thenRetrievedNameIsCorrect() {
//        Mockito.when(notificationService.getProductName()).thenReturn("Mock Product Name");
//        String testName = orderService.getProductName();
//        Assert.assertEquals("Mock Product Name", testName);
//    }

//    @Test
//    public void getNotificationTest(){
//
//
//        try {
//
//            ArrayList<String> realOut = notificationService.getNotification("id1015");
//            notificationService = Mockito.mock(NotificationService.class);
//            ArrayList<String> mockOut = new ArrayList<String>();
//            mockOut.add("comment15");
//            mockOut.add("You have a new comment !");
//            Mockito.when(notificationService.getNotification("id1015")).thenReturn(mockOut);
//
//
//
//            System.out.println(mockOut);
//            System.out.println(realOut);
//            Assert.assertEquals(mockOut, realOut);
//            //Mockito.verify(notificationService).getNotification("id1015");
////            ArrayList<String> order = orderService.getNotification("id1015");
////            System.out.println(order);
////            out = new ArrayList<String>();
////            out.add("comment15");
////            out.add("You have a new comment !");
////            Mockito.when(notificationService.getNotification("id1015")).thenReturn(out);
////            Assert.assertEquals(mockOut, order);
//        } catch (ExecutionException e) {
//            throw new RuntimeException(e);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//    }




    @Test
    public void createNotificationTest(){
        UUID uuid = UUID.randomUUID();
        ArrayList userID = new ArrayList<String>();
        userID.add(uuid.toString());
        Notifications notification = new Notifications();
        notification.setType("message");
        notification.setUserIDs(userID);
        try {
            notificationService.createNotification(notification);
            ArrayList realOut = notificationService.getNotification(String.valueOf(uuid));
            notificationService = Mockito.mock(NotificationService.class);
            ArrayList<String> mockOut = new ArrayList<String>();
            mockOut.add("You have a new message !");
            Mockito.when(notificationService.getNotification(String.valueOf(uuid))).thenReturn(mockOut);



            System.out.println(mockOut);
            System.out.println(realOut);
            Assert.assertEquals(mockOut, realOut);

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }


    @Test
    public void updateNotificationTest(){
        UUID uuid = UUID.randomUUID();
        ArrayList userID = new ArrayList<String>();
        userID.add(uuid.toString());
        Notifications notification = new Notifications();
        notification.setType("TestUpdate");
        notification.setUserIDs(userID);

        try {
            notificationService.updateNotification("TestUpdateDelete",notification);
            ArrayList realOut = notificationService.getNotification(String.valueOf(uuid));
            notificationService = Mockito.mock(NotificationService.class);
            ArrayList<String> mockOut = new ArrayList<String>();
            mockOut.add("TestUpdate");
            Mockito.when(notificationService.getNotification(String.valueOf(uuid))).thenReturn(mockOut);


            System.out.println(mockOut);
            System.out.println(realOut);
            Assert.assertEquals(mockOut, realOut);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    //TODO
    @Test
    public void deleteNotificationTest(){
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        ArrayList userID = new ArrayList<String>();
        userID.add(uuid1.toString());
        userID.add(uuid2.toString());
        Notifications notification = new Notifications();
        notification.setType("TestDelete");
        notification.setUserIDs(userID);
        try {
            notificationService.updateNotification("TestUpdateDelete",notification);
            notificationService.deleteNotification("TestUpdateDelete",uuid1.toString());
            ArrayList realOut = notificationService.getNotification(String.valueOf(uuid1));
            notificationService = Mockito.mock(NotificationService.class);
            ArrayList<String> mockOut = new ArrayList<String>();
            Mockito.when(notificationService.getNotification(String.valueOf(uuid1))).thenReturn(mockOut);

            System.out.println(mockOut);
            System.out.println(realOut);
            Assert.assertEquals(mockOut, realOut);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

}
