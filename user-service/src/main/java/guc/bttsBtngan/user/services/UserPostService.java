package guc.bttsBtngan.user.services;

import guc.bttsBtngan.user.data.UserPostInteraction;
import guc.bttsBtngan.user.data.UserReports;
import guc.bttsBtngan.user.data.UserUserInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.List;
import java.util.Optional;

@Service
public class UserPostService {
    // this class will deal with all user-post interaction and database operations in mongoDB

    @Autowired
    MongoOperations mongoOperations; // this variable contains the mongoDB operations including CRUD operations

    @Autowired
    UserPostRepository userPostRepository; // this variable contains the user-post interaction repository

    @Autowired
    UserRepository userRepository;

    public String getAllFollowers() {
        //TODO: this method will return all followers of the current user
        return "";
    }

    // moderator see reports being made by users
    public String getAllReports(String moderatorId) {
        // TODO: check that the current loggedIn user is a moderator by checking the moderatorId from the logIn session
        // check if current user is moderator
        Optional<UserUserInteraction> isModerator = userRepository.findById(moderatorId);
        if (isModerator.isPresent()) {
            // if current user is moderator, return all reports
            List<UserReports> reports = mongoOperations.findAll(UserReports.class);
            return reports.toString();
        }
        return "";
    }

    public void testMD(){
        System.out.println("Data creation started...");
        List<String> followers = new java.util.ArrayList<>();
        List<String> following = new java.util.ArrayList<>();
        List<String> blockedBy = new java.util.ArrayList<>();
        followers.add("user1");
        followers.add("user2");
        following.add("user3");
        following.add("user4");
        blockedBy.add("user5");
        blockedBy.add("user6");
        UserPostInteraction.UserReports userReports = new UserPostInteraction.UserReports("user1","because?");
        System.out.println("f= "+followers.toString());
        System.out.println("f2= "+following.toString());
        System.out.println("f3= "+blockedBy.toString());
        UserPostInteraction x= new UserPostInteraction("user1", followers, following, blockedBy);
        System.out.println("created");
        mongoOperations.save(x);
        System.out.println("saved"+userReports.toString());
        mongoOperations.save(userReports);
        System.out.println("Data creation finished.");
    }

}
