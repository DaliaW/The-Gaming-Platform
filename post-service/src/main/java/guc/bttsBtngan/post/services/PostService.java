package guc.bttsBtngan.post.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import guc.bttsBtngan.post.data.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;



@Service
public class PostService {
    @Autowired
    MongoOperations mongoOperations;

    public String createPost() throws InterruptedException, ExecutionException {
        Post post = new Post();
        post.setContent("btats potatoeeesss");
        post.setUserId("id gamed");
        post.setModeratorId("3amo moderator");

        mongoOperations.save(post);
        return "DONE, created post is: "+(post).toString();
    }
}