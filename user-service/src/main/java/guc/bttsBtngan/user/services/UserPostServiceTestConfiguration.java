package guc.bttsBtngan.user.services;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;

@Profile("test") //@Profile("guc/bttsBtngan/user/test")
@ContextConfiguration //hbd
// @Configuration
public class UserPostServiceTestConfiguration {
    @Bean
    @Primary
    public UserPostService userPostService(){

        return Mockito.mock(UserPostService.class);
    }


}
