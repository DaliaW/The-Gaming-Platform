package guc.bttsBtngan.post.services;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.*;


import guc.bttsBtngan.post.amqp.RabbitMQConfig;
import guc.bttsBtngan.post.data.Comment;
import guc.bttsBtngan.post.data.Comment.CommentVote;
import guc.bttsBtngan.post.data.Post;
import guc.bttsBtngan.post.data.Post.PostVote;
import guc.bttsBtngan.post.firebase.FirebaseImageService;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;


@Service
public class PostService {
	@Autowired
	FirebaseImageService firebaseImage;
    @Autowired
    MongoOperations mongoOperations;
	@Autowired
	private AmqpTemplate amqpTemplate;

	public String createPost(Post post) throws InterruptedException, ExecutionException {
//    	post.setModeratorId("3amo moderator2");
        mongoOperations.save(post);
        return "DONE, created post is: "+(post).toString();
    }
    
    public String followPost(String userId, String postId)throws InterruptedException, ExecutionException {
    	Query query = new Query(Criteria.where("_id").is(postId));
    	Post post = mongoOperations.findOne(query, Post.class, "post");  	
    	
    	if(post.getPostFollowers() == null)
    	{
    		post.setPostFollowers(new ArrayList<String>());
    	}
    	post.addPostFollower(userId);
    	
    	Update update = new Update().set("postFollowers", post.getPostFollowers());
        mongoOperations.updateFirst(query, update, Post.class);
    	
    	return "DONE, Potatoes follow post : "+(post).toString();
    	
    }

    public String reportPost(String userId, String postId, String reportComment)throws InterruptedException, ExecutionException {
    	Query query = new Query(Criteria.where("_id").is(postId));
    	Post post = mongoOperations.findOne(query, Post.class, "post");

    	Post.PostReport postReport = new Post.PostReport(userId, reportComment);

    	if(post.getPostReports() == null)
    	{
    		post.setPostReports(new ArrayList<Post.PostReport>());
    	}
    	post.addPostReport(postReport);
    	
    	Update update = new Update().set("postReports", post.getPostReports());
        mongoOperations.updateFirst(query, update, Post.class);

    	return "DONE, Potatoes report post : "+(post).toString();

    }

    
    public String commentPost(String userId, String postId, String comment)throws Exception {
    	Query query = new Query();
    	query.addCriteria(Criteria.where("_id").is(postId));
    	Post post = mongoOperations.findOne(query, Post.class, "post");
		if(post==null)throw new Exception("post id is not valid");
    	Comment postComment = new Comment(userId, comment);
		if(post.getComments()==null){
			post.setComments(new ArrayList<>());
		}
    	post.getComments().add(postComment);

    	mongoOperations.save(post);

    	return "DONE, Potatoes comment post : "+(postComment)+" "+post;

    }
    
 
    
    public String commentVote(String userId, String postId, String commentId,boolean vote)throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findById(query, Post.class, "post");
		if(post==null){
			return "post id is not valid";
		}
//		Comment cmnt;
//		for(Comment cmnt2: post.getComments()) {
//			cmnt2.getCommen
//		}
////		todo search for comment
//		Comment cmnt = post.getComments().get(0);//TODO: search for comment by commentId
		Comment cmnt=null;
		for(Comment c:post.getComments()){
			if(c.getId().equals(commentId)){
				cmnt=c;
			}
		}
		if(cmnt==null){
			throw new Exception("comment id is not valid");
		}
		boolean found =false;
		for(CommentVote cv: cmnt.getCommentVotes() ) {
			if(cv.getVoterId().equals(userId)) {
				found =true;
				if(cv.isUpVote()==vote) {
					cmnt.delCommentVote(cv);
				}
				else {
					cv.setUpVote(vote);
	
				}
				
			}
		}
		if(!found) {
			CommentVote cv=new CommentVote(userId,vote);
			cmnt.addCommentVote(cv);
		}
		mongoOperations.save(post);

		return "DONE, Potatoes tag in post : "+(post).toString();

	}
    
    
    public String postVote(String userId, String postId, boolean vote)throws InterruptedException, ExecutionException {
  		Query query = new Query();
  		query.addCriteria(Criteria.where("_id").is(postId));
  		Post post = mongoOperations.findById(query, Post.class, "post");
  		if(post==null){
  			return "post id is not valid";
  		}
  		boolean found = false;
  		for(PostVote pv: post.getPostVotes() ) {
  			if(pv.getVoterId().equals(userId)) {
  				found =true;
  				if(pv.isUpVote()==vote) {
  					post.delPostVote(pv);
  				}
  				else {
  					pv.setUpVote(vote);
  				}
  				
  			}
  		}
  		if(!found) {
  			PostVote pv=new PostVote(userId,vote);
  			post.addPostVote(pv);
  		}
  		mongoOperations.save(post);

  		return "DONE, Potatoes tag in post : "+(post).toString();

  	}
	public String tagInPost(String postId, String[]userIds,String userIdSending)throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		if(post==null){
			throw new Exception("post id is not valid");
		}

		for(String userId:userIds){
			if(!validUserId(userIdSending,userId)){
				throw new Exception("User with user id "+userId+" is not a valid user");
			}
			post.addPostTags(userId);
		}
		Update update = new Update().set("postTags", post.getPostTags());
		mongoOperations.updateFirst(query, update, Post.class);

		return "DONE, Potatoes tag in post : "+(post).toString();

	}

	public String delTagInPost(String postId, String[]userIds)throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		if(post==null){
			throw new Exception("post id is not valid");
		}
		for(String userId:userIds){
			if(!post.getPostTags().contains(userId)){
				new Exception("User with user id "+userId+" is not tagged already in the post");
			}
			post.delPostTags(userId);
		}
		Update update = new Update().set("postTags", post.getPostTags());
		mongoOperations.updateFirst(query, update, Post.class);

		return "DONE, Potatoes tag in post : "+(post).toString();

	}

	public String commentTagInPost(String postId, String commentId, String[]userIds,String userIdSending)throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		if(post==null){
			throw new Exception("post id is not valid");
		}
		Comment cmnt=null;
		for(Comment c:post.getComments()){
			if(c.getId().equals(commentId)){
				cmnt=c;
			}
		}
		if(cmnt==null){
			throw new Exception("comment id is not valid");
		}
		for(String userId:userIds){
			if(!validUserId(userIdSending,userId)){
				throw new Exception("User with user id "+userId+" is not a valid user");
			}
			cmnt.addCommentTags(userId);
		}
		post.getComments().set(0,cmnt);

		Update update = new Update().set("comments", post.getComments());
		mongoOperations.updateFirst(query, update, Post.class);

		return "DONE, Potatoes tag in post : "+(post).toString();

	}

	public String delCommentTagInPost(String postId, String commentId, String[]userIds)throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		if(post==null){
			throw new Exception("post id is not valid");
		}
		Comment cmnt=null;
		for(Comment c:post.getComments()){
			if(c.getId().equals(commentId)){
				cmnt=c;
			}
		}
		if(cmnt==null){
			throw new Exception("comment id is not valid");
		}
		for(String userId:userIds){
			if(!cmnt.getCommentTags().contains(userId)){
				new Exception("User with user id "+userId+" is not tagged already in the comment");
			}
			cmnt.delCommentTags(userId);
		}
		post.getComments().set(0,cmnt);

		Update update = new Update().set("comments", post.getComments());
		mongoOperations.updateFirst(query, update, Post.class);

		return "DONE, Potatoes tag in post : "+(post).toString();

	}
	public boolean validUserId(String me,String suspiciousUser){
		//check first if suspicious user exist in the database

		Map<String, Object> body = new HashMap<>();
		body.put("userId",me);
		final boolean auth_res = (boolean) amqpTemplate.convertSendAndReceive(
				"authentication", body, m -> {
					m.getMessageProperties().setHeader("command", "validateCommand");
					m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);//reply queue
					return m;
				});
		//then check if he is blocked
		final List<String> res = (List<String>) amqpTemplate.convertSendAndReceive(
				"authentication", body, m -> {
					m.getMessageProperties().setHeader("command", "blockedByComman");
					m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);//reply queue
					return m;
				});
		return auth_res && !res.contains(suspiciousUser);
	}
	public String searchPosts(String subContent,String userId) throws InterruptedException, ExecutionException {
		Query query = new Query();
		StringBuilder pattern= new StringBuilder(".*");//starts with anything
		String[]tokens=subContent.split(" ");
		for(String s:tokens){
			pattern.append("(?i)").append(s);//case-insensitive
			pattern.append(".*");//followed by anything
		}
		query.addCriteria(Criteria.where("content").regex(pattern.toString()));
		List<Post> post = mongoOperations.find(query, Post.class, "post");
//		post.removeIf(p -> (!validUserId(userId,p.getUserId())));

		return "DONE, Potatoes report post : "+(post);

	}
	
	// TAG
	public String assignModerator(String postId, String userId)throws InterruptedException, ExecutionException {
		System.out.println("hi");
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
//		Post post = mongoOperations.findOne(query, Post.class, "post");
		
		Update update = new Update().set("moderatorId", userId);
		mongoOperations.updateFirst(query, update, Post.class);
		
		return "DONE, Potatoes report post : ";

	}
	
	public String checkPostReports(String postId, String userId)throws InterruptedException, ExecutionException, ResponseStatusException {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		
		if(!post.getModeratorId().equals(userId)) 
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "access denied");

		return "DONE, Potatoes report post : "+(post.getPostReports());

	}

	// !!!! ama nshoof !!!!
	
	public void attachImageToPost(String post_id,  MultipartFile photo) throws IOException {
	Query query = new Query();
	query.addCriteria(Criteria.where("_id").is(post_id));
	Post post = mongoOperations.findOne(query, Post.class, "post");
	if(post==null){
		//throw exception
	}
	if(photo!=null ){
		String textPath=firebaseImage.save(photo);
		Update update = new Update().set("photoRef", textPath);
        mongoOperations.updateFirst(query, update, Post.class);
	}
}
	
	// !!!! shofna !!!!
	
}