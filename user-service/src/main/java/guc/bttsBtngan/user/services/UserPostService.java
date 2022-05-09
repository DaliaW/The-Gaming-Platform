package guc.bttsBtngan.user.services;

import guc.bttsBtngan.user.data.UserPostInteraction;
import guc.bttsBtngan.user.data.UserReports;
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

    public String getAllFollowers() {
        //TODO: this method will return all followers of the current user
        return "";
    }

    public String getAllReports() {
        List<UserReports> userReports = mongoOperations.findAll(UserReports.class);
        return userReports.toString();
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
