package guc.bttsBtngan.user.data;

import guc.bttsBtngan.user.services.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Arrays;


@Configuration
public class UserDummyData {
    // inserted to postgres
    @Bean
    CommandLineRunner commandLineRunner(UserRepository repository) {
        return args -> {
            BCryptPasswordEncoder p  =new BCryptPasswordEncoder();
            String encoded=p.encode("pass");
            UserUserInteraction User1 = new UserUserInteraction("user1", encoded, "user1@gmail.com", "photo1", false, false);
            UserUserInteraction User2 = new UserUserInteraction("user2", encoded, "user2@gmail.com", "photo2", false, false);
            UserUserInteraction User3 = new UserUserInteraction("user3", encoded, "user3@gmail.com", "photo3", false, false);

            repository.saveAll(Arrays.asList(User1, User2, User3));
        };

    }
}
