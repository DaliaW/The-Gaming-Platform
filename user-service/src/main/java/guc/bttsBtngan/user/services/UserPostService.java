package guc.bttsBtngan.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoOperations;

@Service
public class UserPostService {
    // this class will deal with all user-post interaction and database operations in mongoDB

    @Autowired
    MongoOperations mongoOperations; // this variable contains the mongoDB operations including CRUD operations

}
