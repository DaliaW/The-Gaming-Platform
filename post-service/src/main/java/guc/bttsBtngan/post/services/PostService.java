package guc.bttsBtngan.post.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


import guc.bttsBtngan.post.data.Comment;
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

	public String tagInPost(String postId, String[]userIds)throws InterruptedException, ExecutionException {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findById(query, Post.class, "post");
		if(post==null){
			return "post id is not valid";
		}
		for(String userId:userIds){
			//TODO: check if user id is valid-<<<<<<<<<<<<<<<<<<---
			post.addPostTags(userId);
		}
		mongoOperations.save(post);

		return "DONE, Potatoes tag in post : "+(post).toString();

	}

	public String delTagInPost(String postId, String[]userIds)throws InterruptedException, ExecutionException {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findById(query, Post.class, "post");

		for(String userId:userIds){
			//TODO: check if user id is valid-<<<<<<<<<<<<<<<<<<---
			post.delPostTags(userId);
		}
		mongoOperations.save(post);

		return "DONE, Potatoes tag in post : "+(post).toString();

	}

	public String commentTagInPost(String postId, String commentId, String[]userIds)throws InterruptedException, ExecutionException {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findById(query, Post.class, "post");
		if(post==null){
			return "post id is not valid";
		}
		Comment cmnt = post.getComments().get(0);//TODO: search for comment by commentId
		for(String userId:userIds){
			//TODO: check if user id is valid-<<<<<<<<<<<<<<<<<<---
			cmnt.addCommentTags(userId);
		}
		mongoOperations.save(post);

		return "DONE, Potatoes tag in post : "+(post).toString();

	}

	public String delCommentTagInPost(String postId, String commentId, String[]userIds)throws InterruptedException, ExecutionException {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findById(query, Post.class, "post");
		if(post==null){
			return "post id is not valid";
		}
		Comment cmnt = post.getComments().get(0);//TODO: search for comment by commentId
		for(String userId:userIds){
			//TODO: check if user id is valid-<<<<<<<<<<<<<<<<<<---
			cmnt.delCommentTags(userId);
		}
		mongoOperations.save(post);

		return "DONE, Potatoes tag in post : "+(post).toString();

	}

	public String searchPosts(String subContent)throws InterruptedException, ExecutionException {
		Query query = new Query();
		query.addCriteria(Criteria.where("content").is(subContent));
		List<Post> post = mongoOperations.find(query, Post.class, "post");

		return "DONE, Potatoes report post : "+(post);

	}
}