package guc.bttsBtngan.user.services;

import guc.bttsBtngan.user.data.UserPostInteraction;
import guc.bttsBtngan.user.data.UserReports;
import guc.bttsBtngan.user.data.UserUserInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public String getAllReports(String moderatorId) throws Exception {
        // check if the current user is a moderator
        Optional<UserUserInteraction> moderator = userRepository.findById(moderatorId);
        if (!moderator.get().isModerator()) {
            // if the user is not a moderator
            throw new IllegalStateException("Unauthorized, you are not a moderator!");
        }
        // if current user is moderator, return all reports
        List<UserReports> reports = mongoOperations.findAll(UserReports.class);
        return reports.toString();
    }

    public String blockUser(String currentId, String userId) throws Exception {
        // TODO: get the  current id from the current loggedIn user session
        //assuming current id in the parameters now
        UserPostInteraction myUser = userPostRepository.findByUserId(currentId);

        Optional<UserUserInteraction> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            // if the user does not exist
            throw new Exception("User does not exist");
        }
        UserPostInteraction otherUser = userPostRepository.findByUserId(userId);
        //remove the other from my following and follower list.
        List<String> myFollowers = myUser.getFollowers();
        myFollowers.remove(userId);
        List<String> myFollowing = myUser.getFollowing();
        myFollowing.remove(userId);
        myUser.setFollowers(myFollowers);
        myUser.setFollowing(myFollowing);

        //remove myself from other following and follower list and block myself in their profile.
        List<String> otherFollowers = otherUser.getFollowers();
        otherFollowers.remove(currentId);
        List<String> otherFollowing = otherUser.getFollowing();
        otherFollowing.remove(currentId);
        otherUser.setFollowers(otherFollowers);
        otherUser.setFollowing(otherFollowing);
        List<String> otherBlockedBy = otherUser.getBlockedBy();
        if (otherBlockedBy.contains(currentId))
            throw new Exception("User is already blocked");
        otherBlockedBy.add(currentId);
        otherUser.setBlockedBy(otherBlockedBy);

        userPostRepository.save(otherUser);
        userPostRepository.save(myUser);
        return "User is blocked";
    }

    public String unblockUser(String currentId, String userId) throws Exception {
        // TODO: get the  current id from the current loggedIn user session
        //assuming current id in the parameters now
//        UserPostInteraction myUser = userPostRepository.findByUserId(currentId);

        Optional<UserUserInteraction> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            // if the user does not exist
            throw new Exception("User does not exist");
        }
        UserPostInteraction otherUser = userPostRepository.findByUserId(userId);

        //remove myself from other's blockedBy List.
        List<String> otherBlockedBy = otherUser.getBlockedBy();
        if (!otherBlockedBy.contains(currentId))
            throw new Exception("User is not blocked already");
        otherBlockedBy.remove(currentId);
        otherUser.setBlockedBy(otherBlockedBy);

        userPostRepository.save(otherUser);
        return "User is unblocked";
    }

    public List<String> userRecommendations(String userId) {
        // TODO: get the  userId from the current loggedIn user session
        // TODO: Ask about: I added the recommended user IDs to the returned List,
        // TODO: should we change the response request sheet or include the whole profile in the output ?
        List<String> recommendedUsers = new ArrayList<>();
        UserPostInteraction myUser = userPostRepository.findByUserId(userId);
        List<String> myfollowing = myUser.getFollowing();
        HashSet<String> myfollowingSet = new HashSet<>();
        myfollowingSet.addAll(myfollowing);
        TreeMap<String, Integer> recommendedUsersMap = new TreeMap<>();
        for (int i = 0; i < myfollowing.size(); i++) {
            UserPostInteraction others = userPostRepository.findByUserId(myfollowing.get(i));
            List<String> followingFollowing = others.getFollowing();
            for (int j = 0; j < followingFollowing.size(); j++) {
                String toBeRecommended = followingFollowing.get(j);
                if (!myfollowingSet.contains(toBeRecommended)) {
                    int countOfMutualFollowers = recommendedUsersMap.getOrDefault(toBeRecommended, 0);
                    recommendedUsersMap.put(toBeRecommended, countOfMutualFollowers + 1);
                }
            }
        }
        for (String key : recommendedUsersMap.keySet()) {
            recommendedUsers.add(key);
        }
        return recommendedUsers;
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
