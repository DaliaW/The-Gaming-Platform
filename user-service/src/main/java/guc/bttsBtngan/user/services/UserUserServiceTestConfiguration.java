package guc.bttsBtngan.user.services;

import org.apache.catalina.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import guc.bttsBtngan.user.services.UserUserService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

@Profile("test") //@Profile("guc/bttsBtngan/user/test")
@ContextConfiguration //hbd
// @Configuration
public class UserUserServiceTestConfiguration {
    @Bean
    @Primary
    public UserUserService userUserService(){
        return Mockito.mock(UserUserService.class);
    }


}
