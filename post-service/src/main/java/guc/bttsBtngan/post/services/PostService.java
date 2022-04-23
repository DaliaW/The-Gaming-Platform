package guc.bttsBtngan.post.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


import guc.bttsBtngan.post.data.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;



@Service
public class PostService {
    @Autowired
    MongoOperations mongoOperations;

    public String createPost(Post post) throws InterruptedException, ExecutionException {
        mongoOperations.save(post);
        return "DONE, created post is: "+(post).toString();
    }
    
    public String followPost(String userId, String postId)throws InterruptedException, ExecutionException {
    	Query query = new Query();
    	query.addCriteria(Criteria.where("_id").is(postId));
    	Post post = mongoOperations.findById(query, Post.class, "post");  	
    			
    	post.getPostFollowers().add(userId);
    	
    	mongoOperations.save(post);
    	
    	return "DONE, Potatoes follow post : "+(post).toString();
    	
    }
    
    public String reportPost(String userId, String postId, String reportComment)throws InterruptedException, ExecutionException {
    	Query query = new Query();
    	query.addCriteria(Criteria.where("_id").is(postId));
    	Post post = mongoOperations.findById(query, Post.class, "post");  	
    	
    	Post.PostReport postReport = new Post.PostReport(userId, reportComment);
    	    	
    	post.getPostReports().add(postReport);
    	
    	mongoOperations.save(post);
    	
    	return "DONE, Potatoes report post : "+(post).toString();
    	
    }
}