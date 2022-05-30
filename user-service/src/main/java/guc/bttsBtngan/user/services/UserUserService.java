package guc.bttsBtngan.user.services;

//import com.jlefebure.spring.boot.minio.MinioConfigurationProperties;
//import com.jlefebure.spring.boot.minio.MinioException;
//import com.jlefebure.spring.boot.minio.MinioService;

import guc.bttsBtngan.user.data.UserPostInteraction;
import guc.bttsBtngan.user.data.UserReports;
import guc.bttsBtngan.user.data.UserUserInteraction;
import guc.bttsBtngan.user.firebase.FirebaseImageService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.*;

@Service // to specify that this class is responsible for the business logic
public class UserUserService {
    @Autowired
    FirebaseImageService firebaseImage;
    // this class will deal with all user-user interaction and database operations in postgres

    @Autowired
    private AmqpTemplate amqpTemplate;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserPostRepository userPostRepository;


    private final UserRepository userRepository;
    // the repository for the user table in postgres to deal with database operations including CRUD operations

    // private PasswordEncoder passwordEncoder;

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    public UserUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        int strength=10;

        this.passwordEncoder=new BCryptPasswordEncoder(strength,new SecureRandom());
    }

    @GetMapping
    public String getAllUsers() {
        return userRepository.findAll().toString();
    }



    public String registerUser(UserUserInteraction user) {
        // check that username, email and password are not empty
        if (user.getusername().equals("") || user.getEmail().equals("") || user.getPassword().equals("")) {
            throw new IllegalArgumentException("Username, email and password cannot be empty");
        }
        // check if the email is already registered
        Optional<UserUserInteraction> email = userRepository.findByEmail(user.getEmail());
        if (email.isPresent()) {
            // if the user email already exists
            throw new IllegalStateException("Email already exists");
        }

        // verify email format
        if (!user.getEmail().matches("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+")) {
            // if the email is not in the correct format
            throw new IllegalStateException("Email is not in the correct format");
        }

        // check if the username already exists
        Optional<UserUserInteraction> username = userRepository.findByUsername(user.getusername());
        if (username.isPresent()) {
            // if the user username already exists
            throw new IllegalStateException("Username already exists");
        }

        // password hashing
        int strength = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(strength, new SecureRandom());
        String encryptedPassword= bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);

        // if the email is not registered yet then save the user
        // String encryptedPassword= passwordEncoder.encode(user.getPassword());
        // user.setPassword(encryptedPassword);
        userRepository.save(user);

        // initialize the user's followers, following & report with empty lists
        List<String> EmptyList = Collections.<String>emptyList();
        List<UserReports> EmptyReports = Collections.<UserReports>emptyList();
        UserPostInteraction userMongo = new UserPostInteraction(null, EmptyList, EmptyList, EmptyList, EmptyReports); // enhance this  <==
        // set the id of the user in sql to mongo
        Optional<UserUserInteraction> userId = userRepository.findById(user.getid());
        System.out.println(userId.get().getid() + "here");
        userMongo.setUserId(userId.get().getid());
        mongoOperations.save(userMongo);
        return "User registered successfully";
    }

//    public void deleteUser(String id) {
//        // delete a user
//        boolean exists = userRepository.existsById(id);
//        // check if the user exists
//        if (!exists) {
//            // if the user does not exist
//            throw new IllegalStateException("User does not exist");
//        }
//        // if the user exists then delete the user
//        userRepository.deleteById(id);
//    }



    @Transactional
    public String updateUser(String id, String username, String email, String oldPassword, String newPassword, MultipartFile photo) throws IOException {

        // update a user
        UserUserInteraction user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User does not exist"));

        // if the name not equal to null, not empty & not the same as the current name
        if(username != null && username.length() > 0 && !Objects.equals(username, user.getusername())) {
            // update the name
            user.setusername(username);
        }
        // if email is not null, not empty & not the same as the current email & not already have been taken
        if(email != null && email.length() > 0 && !Objects.equals(email, user.getEmail())){
            Optional<UserUserInteraction> emailExists = userRepository.findByEmail(email);
            // check if the email is already registered
            if (!emailExists.isPresent()) {
                user.setEmail(email);

            } else {
                throw new IllegalStateException("Email already exists");

            }

        }
        // if password is not null, not empty & not the same as the current password
        if((oldPassword ==null ||oldPassword.length()==0)&& newPassword!=null) {
            throw new IllegalStateException("Please enter old password.");
        }
        if(oldPassword != null && oldPassword.length()>0){

//            if(!Objects.equals(encryptedPassword, user.getPassword())) {
            if(!passwordEncoder.matches(oldPassword,user.getPassword())){
                throw new IllegalStateException("Old Password is incorrect.");
            }
            else if(newPassword!=null && newPassword.length()>0){
                if(!passwordEncoder.matches(newPassword,user.getPassword())){
                    String encryptedNewPassword= passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedNewPassword);
                }
                else
                    throw new IllegalStateException("New password is same as old password.");

            }
            else
                throw new IllegalStateException("Please enter new password");
        }
        if(photo!=null && !photo.isEmpty()){
            String textPath=firebaseImage.save(photo);
            user.setPhotoRef(textPath);
        }


        return "Profile updated successfully";

    }
    @Transactional
    public String deleteProfilePicture(String id) throws IOException {
        UserUserInteraction user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User does not exist"));
        String photoRef= user.getPhotoRef();
        if(photoRef!=null && photoRef.length()>0) {
            firebaseImage.delete(photoRef);
            user.setPhotoRef("");

        }
        else{
            throw new IllegalStateException("No photo to delete.");
        }
        return "Profile picture deleted successfully";


    }
    public String getAllphotoRef(String photoRef) {
        Optional<UserUserInteraction> user = userRepository.findByphotoRef(photoRef);
        return userRepository.findByphotoRef(photoRef).toString();
    }

    // moderator ban user
    public String banUser(String moderatorId ,String userId) {
        // check if the user is already banned
        if (userRepository.findById(userId).get().isBanned()) {
            // if the user is already banned
            throw new IllegalStateException("User is already banned");
        }
        // check if the current user is a moderator
        Optional<UserUserInteraction> moderator = userRepository.findById(moderatorId);
        if (!moderator.get().isModerator()) {
            // if the user is not a moderator
            throw new IllegalStateException("Unauthorized, you are not a moderator!");
        }
        // check if the user to be banned exists
        Optional<UserUserInteraction> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            // if the user does not exist
            throw new IllegalStateException("User does not exist");
        }
        // if the user exists then ban the user
        if(moderator != user ) {
            user.get().setBanned(true);
            userRepository.save(user.get());    // save the user
            return "User banned successfully";
        }
        else
            throw new IllegalStateException("You cannot ban yourself");
    }

    // moderator unban user
    public String unbanUser(String moderatorId, String userId) {
        // check if the user is already unbanned
        if (!userRepository.findById(userId).get().isBanned()) {
            // if the user is already unbanned
            throw new IllegalStateException("User is already unbanned");
        }
        // check if the current user is a moderator
        Optional<UserUserInteraction> moderator = userRepository.findById(moderatorId);
        if (!moderator.get().isModerator()) {
            // if the user is not a moderator
            throw new IllegalStateException("Unauthorized, you are not a moderator!");
        }
        // check if the user exists
        Optional<UserUserInteraction> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            // if the user does not exist
            throw new IllegalStateException("User does not exist");
        }
        // if the user exists then unban the user
        user.get().setBanned(false);
        userRepository.save(user.get());    // save the user
        return "User unbanned successfully";
    }

    public String DeleteUser(String userId) throws Exception {
        UserPostInteraction user = userPostRepository.findByUserId(userId);
        Optional<UserUserInteraction> userX = userRepository.findById(userId);
        // check if the user exists
        System.out.println("user:  "+user);
        System.out.println("userX:  "+userX);

        if (!userX.isPresent()) {
            // if the user does not exist
            System.out.println("user hereeeeeee ");
            throw new IllegalStateException("User does not exist here ");
        }

        //remove the user from their following and followers list.
        List<String> followers = user.getFollowers();
        List<String> following = user.getFollowing();
        if(!followers.isEmpty()){
            for(int i=0;i< followers.size();i++){
                // OTHER USERS FOLLOW ME !!!!!!!
                // loop over each of the followers, get their id,
                // remove the user id from their following list
                String followId= followers.get(i);
                System.out.println(followId);
                UserPostInteraction follower = userPostRepository.findByUserId(followId);
                List<String> FollowFolloweringsList = follower.getFollowing();
                // user following list, other user followers list
                for(int j=0;j<FollowFolloweringsList.size();j++){
                    if(FollowFolloweringsList.get(i).equals(userId)) {
                        // I reached the user id in the following list
                        FollowFolloweringsList.remove(userId);
                        follower.setFollowing(FollowFolloweringsList);

                        Query query =new Query();
                        query.addCriteria(Criteria.where("userId").is(followId));
                        Update deleteAFollowing=new Update().set("following",FollowFolloweringsList);
                        mongoOperations.updateFirst(query,deleteAFollowing,UserPostInteraction.class);
                        // userPostRepository.save(follower);
                    }
                }
            }
        }
        else{
            System.out.println(" ana follers fadyyyyyyyyyyy");

        }
        if(!following.isEmpty()){
            for(int i=0;i< following.size();i++){
                // I follow other USERS !!!!!!!
                // loop over each of the following, get their id,
                // remove the user id from their followers list
                String followId= followers.get(i);
                System.out.println(followId);
                UserPostInteraction follow = userPostRepository.findByUserId(followId);
                List<String> FollowFollowersList = follow.getFollowers();
                // user following list, other user followers list
                for(int j=0;j<FollowFollowersList.size();j++){
                    if(FollowFollowersList.get(i).equals(userId)) {
                        FollowFollowersList.remove(userId);
                        follow.setFollowers(FollowFollowersList);

                        Query query2 =new Query();
                        query2.addCriteria(Criteria.where("userId").is(followId));
                        Update deleteAFollower=new Update().set("followers",FollowFollowersList);
                        mongoOperations.updateFirst(query2,deleteAFollower,UserPostInteraction.class);

                        // userPostRepository.save(follow);

                    }
                }
            }
        }
        else{
            System.out.println(" ana following fadyyyyyyyyyyy");

        }

        Query query1 =new Query();
        query1.addCriteria(Criteria.where("userId").is(userId));
//        UserPostInteraction userTest2 = mongoOperations.findOne(query1, UserPostInteraction.class);
        mongoOperations.remove(query1,UserPostInteraction.class);
//                remove(user);
        System.out.println("Deleted document : " + user);
//        // if the user exists then delete the user
        userRepository.deleteById(userId);
        //userPostRepository.delete(user);
        //mongoOperations.save(user);
        return "User account has been successfully deleted";
    }
    // new
    void notify(ArrayList<String>follow){
        HashMap<String, Object> type_ID= new HashMap<String, Object>();
        type_ID.put("type", "You've a new follower");
        type_ID.put("userID", follow);
        amqpTemplate.convertAndSend("notification_req",type_ID,  m -> {
            m.getMessageProperties().setHeader("command", "createNotificationCommand");
            System.out.printf("Sending message: %s", m.getBody());
            return m;
        });
        System.out.printf("Notification sent to %s", follow);
    }
    public String followUser(String userId,String userToBeFollowedId) {
        UserPostInteraction user= userPostRepository.findByUserId(userId);
        UserPostInteraction followedUser=userPostRepository.findByUserId(userToBeFollowedId);

        System.out.printf("userId: %s", userId);
        System.out.printf("userToBeFollowedId: %s", userToBeFollowedId);

        List<String> userFollowings= user.getFollowing();
        if(userFollowings==null)
            userFollowings=new ArrayList<>();
        userFollowings.add(userToBeFollowedId);
        user.setFollowing(userFollowings);
        List<String> userFollowers=followedUser.getFollowers();
        if(userFollowers==null)
            userFollowers=new ArrayList<>();
        userFollowers.add(userId);
        followedUser.setFollowers(userFollowers);
        Query query =new Query();
        query.addCriteria(Criteria.where("userId").is(userToBeFollowedId));
        Update updateFollowers=new Update().set("followers",userFollowers);
        mongoOperations.updateFirst(query,updateFollowers,UserPostInteraction.class);
        Query query1 =new Query();
        query1.addCriteria(Criteria.where("userId").is(userId));
        Update updateFollowing=new Update().set("following",userFollowings);
        mongoOperations.updateFirst(query1,updateFollowing,UserPostInteraction.class);

        // notify the user that he has been followed
        ArrayList<String> userToBeNotified=new ArrayList<String>();
        userToBeNotified.add(user.getUserId());
        notify(userToBeNotified);

        return "you are following this user now";
    }

    public String unfollowUser(String userId,String userToBeUnfollowedId) {
        UserPostInteraction user= userPostRepository.findByUserId(userId);
        UserPostInteraction unfollowedUser=userPostRepository.findByUserId(userToBeUnfollowedId);
        List<String> userFollowings= user.getFollowing();
        if(userFollowings==null)
            userFollowings=new ArrayList<>();
        userFollowings.remove(userToBeUnfollowedId);
        user.setFollowing(userFollowings);
        List<String> userFollowers=unfollowedUser.getFollowers();
        if(userFollowers==null)
            userFollowers=new ArrayList<>();
        userFollowers.remove(userId);
        unfollowedUser.setFollowers(userFollowers);
        Query query =new Query();
        query.addCriteria(Criteria.where("userId").is(userToBeUnfollowedId));
        Update updateFollowers=new Update().set("followers",userFollowers);
        mongoOperations.updateFirst(query,updateFollowers,UserPostInteraction.class);
        Query query1 =new Query();
        query1.addCriteria(Criteria.where("userId").is(userId));
        Update updateFollowing=new Update().set("following",userFollowings);
        mongoOperations.updateFirst(query1,updateFollowing,UserPostInteraction.class);
        return "you are not following this user anymore";
    }


}
