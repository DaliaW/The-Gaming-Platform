package guc.bttsBtngan.user.services;

import guc.bttsBtngan.user.data.UserPostInteraction;
import guc.bttsBtngan.user.data.UserReports;
import guc.bttsBtngan.user.data.UserUserInteraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    //=============== REPORT USERS :- =============================================
    public String reportUser(String userId, String userId2, String reportComment) throws Exception {
        UserPostInteraction user = userPostRepository.findByUserId(userId2);

        Optional<UserUserInteraction> userX = userRepository.findById(userId);
        System.out.println("here is the data "+ userId+ "  "+ userId2+"   "+ reportComment);
        if (!userX.isPresent()) {
            // if the user does not exist
            throw new Exception("User does not exist");
        }
        if(reportComment.isEmpty()){
//            System.out.println("ana fadyyy ahoooo");
            reportComment=" ";
        }
        // check if the user exists
        System.out.println("userX: "+userX);
        Optional<UserUserInteraction> userX2 = userRepository.findById(userId2);
        // check if the user exists
        System.out.println("userX2: "+userX2);
        if (!userX2.isPresent()) {
            // if the user does not exist
            System.out.println("ana mesh mawgood");
            throw new IllegalStateException("User does not exist");
        }
//        UserPostInteraction user = userPostRepository.findByUserId(userId2);
//        System.out.println("ana hena user ahooooooo "+user);

        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId2));
//        UserPostInteraction user = mongoOperations.findById(query, UserPostInteraction.class);
        System.out.println("user from mongo : "+user);

        UserReports report = new UserReports(userId, reportComment);
        System.out.println("the report made : "+report);
        List<UserReports> getAllUserReports= user.getUserReports();
        if(getAllUserReports.isEmpty()){
            System.out.println("ana fadyyyyy");
            getAllUserReports= new ArrayList<>();
        }
        System.out.println("get all, users report "+getAllUserReports);

        // HANDLE THE REPITION OF THE REPORTS INSIDE THE DATABASE
        for(int i=0;i<getAllUserReports.size();i++){
            UserReports newReports= getAllUserReports.get(i);
            System.out.println(newReports);
            String user1= newReports.getIssuerId();
            String comment1= newReports.getComment();
            System.out.println("user1 : "+user1);
            System.out.println("user : "+user);
            System.out.println("comment 1: "+comment1);
            System.out.println("report comment : "+reportComment);
            System.out.println("user1.equals(user) "+user1.equalsIgnoreCase(userId));
            System.out.println("comment1.equals(reportcomment)  "+comment1.equals(reportComment));

            if(user1.equals(userId)&& comment1.equals(reportComment)){
                System.out.println("ana the same message");
                throw new Exception("The user has been already reported with the same comment.");
            }
        }

        System.out.println("ana hena ahooooooo");


        getAllUserReports.add(report);
        user.setUserReports(getAllUserReports);
        System.out.println("AFTER ADDING "+user);

        Update reportAdd=new Update().set("userReports",getAllUserReports);

        mongoOperations.updateFirst(query,reportAdd,UserPostInteraction.class);
        System.out.println("report added document : " + reportAdd);
        System.out.println("report user  : " + user);
//        mongoOperations.save(user);

        return " the report has been posted successfully: ";
    }

}
