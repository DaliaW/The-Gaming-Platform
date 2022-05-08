package test;

import guc.bttsBtngan.notification.service.NotificationService;
import guc.bttsBtngan.notification.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@SpringBootTest
@ActiveProfiles("test")
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


}
