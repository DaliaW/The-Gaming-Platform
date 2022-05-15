package guc.bttsBtngan.user.services;

import com.jlefebure.spring.boot.minio.MinioConfiguration;
import com.jlefebure.spring.boot.minio.MinioConfigurationProperties;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import guc.bttsBtngan.user.data.UserPostInteraction;
import guc.bttsBtngan.user.data.UserReports;
import guc.bttsBtngan.user.data.UserUserInteraction;
import guc.bttsBtngan.user.data.UserPostInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.data.mongodb.core.query.Query;



import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.*;

@Service // to specify that this class is responsible for the business logic
public class UserUserService {

    @Autowired
    private MinioService minioService;
    @Autowired
    MongoOperations mongoOperations;
    @Autowired
    private MinioConfigurationProperties minioConfigurationProperties;
    // this class will deal with all user-user interaction and database operations in postgres

    private final UserRepository userRepository;
    private final UserPostRepository userPostRepository;
    // the repository for the user table in postgres to deal with database operations including CRUD operations

    @Autowired
    public UserUserService(UserRepository userRepository,UserPostRepository userPostRepository,MongoOperations mongoOperations) {
        this.userRepository = userRepository;
        this.userPostRepository=userPostRepository;

    }

    @GetMapping
    public String getAllUsers() {
        return userRepository.findAll().toString();
    }
    public String getAllUsersMongo() {
        return userPostRepository.findAll().toString();
    }
    public String getUser(String id) {
        Optional<UserUserInteraction> userId = userRepository.findById(id);
        return userId.toString();
    }

    public String registerUser(UserUserInteraction user) {
        // register a user
        Optional<UserUserInteraction> email = userRepository.findByEmail2(user.getEmail());
        // check if the email is already registered
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
        Optional<UserUserInteraction> username = userRepository.findByUsername(user.getUserName());
        if (username.isPresent()) {
            // if the user username already exists
            throw new IllegalStateException("Username already exists");
        }

        // password hashing
        System.out.println("password: " + user.getPassword());  // for testing
        int strength = 10;
        BCryptPasswordEncoder bCryptPasswordEncoder =
                new BCryptPasswordEncoder(strength, new SecureRandom());
        String encryptedPassword= bCryptPasswordEncoder.encode(user.getPassword());
        System.out.println("encrypted password: " + encryptedPassword);  // for testing
        user.setPassword(encryptedPassword);

        // if the email is not registered yet then save the user
        userRepository.save(user);

        // initialize the user's followers, following & report with empty lists
        List<String> EmptyList = Collections.<String>emptyList();
        List<UserReports> EmptyReports = Collections.<UserReports>emptyList();
        UserPostInteraction userMongo = new UserPostInteraction(null, EmptyList, EmptyList, EmptyList, EmptyReports); // enhance this  <==
        // set the id of the user in sql to mongo
        Optional<UserUserInteraction> userId = userRepository.findById(user.getUserId());
        System.out.println(userId.get().getUserId() + "here");
        userMongo.setUserId(userId.get().getUserId());
        mongoOperations.save(userMongo);
        return "User registered successfully";
    }

    public void deleteUser(String id) {
        // delete a user
        boolean exists = userRepository.existsById(id);
        // check if the user exists
        if (!exists) {
            // if the user does not exist
            throw new IllegalStateException("User does not exist");
        }
        // if the user exists then delete the user
        userRepository.deleteById(id);
    }

    @Transactional
    public String updateUser(String id, String username, String email, String oldPassword, String newPassword, MultipartFile photo) throws IOException, MinioException {

        // update a user
        UserUserInteraction user = userRepository.findById(id).orElseThrow(() -> new IllegalStateException("User does not exist"));

        // if the name not equal to null, not empty & not the same as the current name
        if(username != null && username.length() > 0 && !Objects.equals(username, user.getUserName())) {
            // update the name
            user.setUserName(username);
        }
        // if email is not null, not empty & not the same as the current email & not already have been taken
        if(email != null && email.length() > 0 && !Objects.equals(email, user.getEmail())){
            Optional<UserUserInteraction> emailExists = userRepository.findByEmail2(email);
            // check if the email is already registered
            if (emailExists.isPresent()) {
                // if the user email already exists
                throw new IllegalStateException("Email already exists");
            } else {
                // update the email
                user.setEmail(email);
            }
        }
        // if password is not null, not empty & not the same as the current password
        if(oldPassword != null && oldPassword.length()>0){
            if( !Objects.equals(oldPassword, user.getPassword())) {
                throw new IllegalStateException("Old Password is incorrect.");
            }
            else if(newPassword!=null && newPassword.length()>0){
                if(!Objects.equals(newPassword, user.getPassword())){
                    user.setPassword(newPassword);
                }
                else
                    throw new IllegalStateException("New password is same as old password.");

            }
            else
                throw new IllegalStateException("Please enter new password");
        }
        if(photo!=null ){
            String textPath=minioConfigurationProperties.getBucket();
            textPath+="/";
            String imgName= photo.getOriginalFilename();
            textPath+=imgName;
            Path source = Paths.get(textPath);
            InputStream file=photo.getInputStream();
            String contentType=photo.getContentType();
            minioService.upload(source,file,contentType);
        }


        return "User updated successfully";

    }

    public String getAllphotoRef(String photoRef) {
        Optional<UserUserInteraction> user = userRepository.findByphotoRef(photoRef);
        return userRepository.findByphotoRef(photoRef).toString();
//        System.out.println(userRepository.findByphotoRef(photoRef).toString());
//        return "hello";
    }

    // moderator ban user by id
    public String banUser(String userId) {
        // TODO: get the moderator id from the current loggedIn user session
        // check if the user is already banned
        if (userRepository.findById(userId).get().isBanned()) {
            // if the user is already banned
            throw new IllegalStateException("User is already banned");
        }
        // check if the current user is a moderator
//        Optional<UserUserInteraction> moderator = userRepository.findById(moderatorId);
        // check if the user is a moderator
//        if (!moderator.get().isModerator()) {
//            // if the user is not a moderator
//            throw new IllegalStateException("User is not a moderator");
//        }
        // check if the user exists
        Optional<UserUserInteraction> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            // if the user does not exist
            throw new IllegalStateException("User does not exist");
        }
        // if the user exists then ban the user
        user.get().setBanned(true);
        return "User banned";
    }

    public String unbanUser(String userId) {
        // TODO: get the moderator id from the current loggedIn user session
        // check if the user is already unbanned
        if (!userRepository.findById(userId).get().isBanned()) {
            // if the user is already unbanned
            throw new IllegalStateException("User is already unbanned");
        }
        // check if the current user is a moderator
//        Optional<UserUserInteraction> moderator = userRepository.findById(moderatorId);
//        if (!moderator.get().isModerator()) {
//            // if the user is not a moderator
//            throw new IllegalStateException("User is not a moderator");
//        }
        // check if the user exists
        Optional<UserUserInteraction> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            // if the user does not exist
            throw new IllegalStateException("User does not exist");
        }
        // if the user exists then unban the user
        user.get().setBanned(false);
        return "User unbanned";
    }

    public String followUser(String userId,String userToBeFollowedId) {
        UserPostInteraction user= userPostRepository.findByUserId(userId);
        UserPostInteraction followedUser=userPostRepository.findByUserId(userToBeFollowedId);
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
