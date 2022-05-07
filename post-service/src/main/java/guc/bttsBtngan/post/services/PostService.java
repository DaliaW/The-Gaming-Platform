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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



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
	static char getAnother(char c){
		if(c>='a' && c<='z'){
			int x=c-'a';
			return (char)('A'+x);
		}
		if(c>='A' && c<='Z'){
			int x=c-'A';
			return (char)('a'+x);
		}
		return c;
	}
	public String searchPosts(String subContent)throws InterruptedException, ExecutionException {
		Query query = new Query();
		StringBuilder pattern= new StringBuilder(".*");//starts with anything
		String[]tokens=subContent.split(" ");
		for(String s:tokens){
			pattern.append("(?i)").append(s);//case-insensitive
			pattern.append(".*");//followed by anything
		}
		query.addCriteria(Criteria.where("content").regex(pattern.toString()));
		List<Post> post = mongoOperations.find(query, Post.class, "post");

		return "DONE, Potatoes report post : "+(post);

	}
	
	
	public String assignModerator(String postId, String userId)throws InterruptedException, ExecutionException {
		System.out.println("hi");
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.find(query, Post.class, "post").get(0);
		
		post.setModeratorId(userId);
		mongoOperations.save(post);
		System.out.println("bye");
		
		return "DONE, Potatoes report post : "+(post);

	}
	
	public String checkPostReports(String postId, String userId)throws InterruptedException, ExecutionException, ResponseStatusException {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.find(query, Post.class, "post").get(0);
		
		if(!post.getModeratorId().equals(userId)) 
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "access denied");

		return "DONE, Potatoes report post : "+(post.getPostReports());

	}
}