//package guc.bttsBtngan.user.data;
//
//import guc.bttsBtngan.user.services.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Arrays;
//
//
//@Configuration
// // to be used once then comment it out
//public class UserDummyData {
//    // inserted to postgres
//    @Bean
//    CommandLineRunner commandLineRunner(UserRepository repository) {
//        return args -> {
//            UserUserInteraction User1 = new UserUserInteraction("user1", "pass", "user1@gmail.com", "photo1", false, false);
//            UserUserInteraction User2 = new UserUserInteraction("user2", "pass", "user2@gmail.com", "photo2", false, false);
//            UserUserInteraction User3 = new UserUserInteraction("user3", "pass", "user3@gmail.com", "photo3", false, false);
//
//            repository.saveAll(Arrays.asList(User1, User2, User3));
//        };
//
//    }
//}
