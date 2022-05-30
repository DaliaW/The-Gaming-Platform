package guc.bttsBtngan.notification.service;

import guc.bttsBtngan.notification.service.NotificationService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;

@Profile("test") //@Profile("guc/bttsBtngan/notification/test")
@ContextConfiguration //hbd //@Configuration
public class NotificationServiceTestConfiguration {
    @Bean
    @Primary
    public NotificationService notificationService(){
        return Mockito.mock(NotificationService.class);
    }
}
