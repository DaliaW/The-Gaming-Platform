package guc.bttsBtngan.post.services;

import guc.bttsBtngan.post.services.PostService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;

@Profile("test") //@Profile("guc/bttsBtngan/notification/test")
@ContextConfiguration //hbd //@Configuration
public class PostServiceTestConfiguration {
    @Bean
    @Primary
    public PostService postService(){
        return Mockito.mock(PostService.class);
    }
}
