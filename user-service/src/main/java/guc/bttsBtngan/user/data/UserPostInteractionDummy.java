package guc.bttsBtngan.user.data;

import guc.bttsBtngan.user.services.UserPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
@Configuration
public class UserPostInteractionDummy {
//    @Autowired
//    UserPostRepository userPostRepository;
//
//    void createDummyData() {
//        System.out.println("Data creation started...");
//        List<String> followers = new java.util.ArrayList<>();
//        List<String> following = new java.util.ArrayList<>();
//        List<String> blockedBy = new java.util.ArrayList<>();
//        followers.add("user1");
//        followers.add("user2");
//        following.add("user3");
//        following.add("user4");
//        blockedBy.add("user5");
//        blockedBy.add("user6");
//        userPostRepository.save(new UserPostInteraction("user1", followers, following, blockedBy));
//        System.out.println("Data creation finished.");
//    }
}
