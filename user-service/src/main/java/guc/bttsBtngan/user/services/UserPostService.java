package guc.bttsBtngan.user.services;

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
}
