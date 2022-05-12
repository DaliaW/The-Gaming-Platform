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

    public String blockUser(String currentId, String userId) {
        // TODO: get the  current id from the current loggedIn user session
        //assuming current id in the parameters now
        UserPostInteraction myUser = userPostRepository.findByUserId(currentId);

        Optional<UserUserInteraction> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            // if the user does not exist
            throw new IllegalStateException("User does not exist");
        }
        UserPostInteraction otherUser = userPostRepository.findByUserId(userId);

        //remove the other from my following and follower list and block them.
        List<String> myFollowers = myUser.getFollowers();
        myFollowers.remove(userId);
        List<String> myFollowing = myUser.getFollowing();
        myFollowing.remove(userId);
        myUser.setFollowers(myFollowers);
        myUser.setFollowing(myFollowing);
        List<String> myBlockedBy = myUser.getBlockedBy();
        if(myBlockedBy.contains(userId))
            return "User is already blocked";
        myBlockedBy.add(userId);
        myUser.setBlockedBy(myBlockedBy);

        //remove the myself from other following and follower list and block myself in their profile.
        List<String> otherFollowers = otherUser.getFollowers();
        otherFollowers.remove(currentId);
        List<String> otherFollowing = otherUser.getFollowing();
        otherFollowing.remove(currentId);
        otherUser.setFollowers(otherFollowers);
        otherUser.setFollowing(otherFollowing);
        List<String> otherBlockedBy = otherUser.getBlockedBy();
        if(otherBlockedBy.contains(currentId))
            return "User is already blocked";
        otherBlockedBy.add(currentId);
        otherUser.setBlockedBy(otherBlockedBy);

        return "User is blocked";
    }

    public String unblockUser(String currentId, String userId) {
        // TODO: get the  current id from the current loggedIn user session
        // TODO: Ask about: what if the user who I am unblocking is still blocking me ?!
        //assuming current id in the parameters now
        UserPostInteraction myUser = userPostRepository.findByUserId(currentId);

        Optional<UserUserInteraction> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            // if the user does not exist
            throw new IllegalStateException("User does not exist");
        }
        UserPostInteraction otherUser = userPostRepository.findByUserId(userId);

        //remove the other my blockedBy list
        List<String> myBlockedBy = myUser.getBlockedBy();
        if(!myBlockedBy.contains(userId))
            return "User is not blocked";
        myBlockedBy.remove(userId);
        myUser.setBlockedBy(myBlockedBy);

        //remove myself from other's blockedBy List.
        List<String> otherBlockedBy = otherUser.getBlockedBy();
        if(!otherBlockedBy.contains(currentId))
            return "User is already blocked";
        otherBlockedBy.remove(currentId);
        otherUser.setBlockedBy(otherBlockedBy);
        return "User is unblocked";
    }

//    public void testMD(){
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
//        UserPostInteraction.UserReports userReports = new UserPostInteraction.UserReports("user1","because?");
//        System.out.println("f= "+followers.toString());
//        System.out.println("f2= "+following.toString());
//        System.out.println("f3= "+blockedBy.toString());
//        UserPostInteraction x= new UserPostInteraction("user1", followers, following, blockedBy);
//        System.out.println("created");
//        mongoOperations.save(x);
//        System.out.println("saved"+userReports.toString());
//        mongoOperations.save(userReports);
//        System.out.println("Data creation finished.");
//    }

}
