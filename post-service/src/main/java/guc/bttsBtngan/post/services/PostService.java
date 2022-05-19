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
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        
        
        // check if the user who posted the post has followers
        // if the list contains followers, we do the notification step (create hash map with Type,list<string>)
            
//        HashMap<String, Object> type_IDs= new HashMap<String, Object>();
//	  	ArrayList<String> btngan = new ArrayList<String>();  ;
//	  	type_IDs.put("type", "post");
//	  	type_IDs.put("userIDs", btngan);
//        amqpTemplate.convertAndSend("notification_req",type_IDs,  m -> {
//            m.getMessageProperties().setHeader("command", "createNotificationCommand");
//            return m;
//        });
        
     /////////////////////////////////////////////////////////////////////////////   
      
      
        return "DONE, created post is: "+(post).toString();
    }
    
    public String followPost(String userId, String postId, boolean follow)throws Exception {
    	Query query = new Query(Criteria.where("_id").is(postId));
    	Post post = mongoOperations.findOne(query, Post.class, "post");  	
    	
    	if(post.getPostFollowers() == null)
    	{
    		post.setPostFollowers(new ArrayList<String>());
    	}
    	int followIdx = -1;
    	int followersCount = post.getPostFollowers().size();
    	for(int i=0;i<followersCount;i++)
    	{
    		String currFollower = post.getPostFollowers().get(i);
    		if(currFollower.equals(userId))
    		{
    			followIdx = i;
    			break;
    		}
    	}
    	// If I am a follower to the post and I want to unfollow
    	if(followIdx!=-1 && !follow)
    	{
    		post.getPostFollowers().remove(followIdx);
    	}
    	// If I am not a follower to the post and I want to follow
    	else if(followIdx == -1 && follow)
    	{
    		post.addPostFollower(userId);
    	}
    	else 
    	{
    		// following and trying to follow
    		if(follow)
    		{
    			throw new Exception("Cannot do follow because you are already following");
    		}
    		// either not following and trying to unfollow
    		else
    		{
    			throw new Exception("Cannot do unfollow because you are already not following");    			
    		}
    	}
    	
    	post.setNoOfFollwer(post.getPostFollowers().size());
    	
    	
    	Update update = new Update().set("postFollowers", post.getPostFollowers())
    								.set("noOfFollwer", post.getNoOfFollwer());
        mongoOperations.updateFirst(query, update, Post.class);
    	
    	return "request done!";
    	
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

	void notifyFollowersOfPost(ArrayList<String>followers,String userId){
        HashMap<String, Object> type_IDs= new HashMap<String, Object>();
	  	type_IDs.put("type", "post by "+userId+" is created");
	  	type_IDs.put("userIDs", followers);
        amqpTemplate.convertAndSend("notification_req",type_IDs,  m -> {
            m.getMessageProperties().setHeader("command", "createNotificationCommand");
            return m;
        });
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
		notifyFollowersOfPost(post.getPostFollowers(),post.getUserId());
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
			if(!validateUserId(userIdSending,userId)){
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
			if(!validateUserId(userIdSending,userId)){
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
	public boolean validateUserId(String me,String suspiciousUser){

		Map<String, Object> body = new HashMap<>();
		body.put("userId",me);
		//check first if suspicious user exist in the database
//		final boolean auth_res = (boolean) amqpTemplate.convertSendAndReceive(
//				"authentication", body, m -> {
//					m.getMessageProperties().setHeader("command", "validateCommand");
//					m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);//reply queue
//					return m;
//				});
		//then check if he is blocked
		final List<String> res = (List<String>) amqpTemplate.convertSendAndReceive(
				"authentication", body, m -> {
					m.getMessageProperties().setHeader("command", "blockedByComman");
					m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);//reply queue
					return m;
				});
		return true && !res.contains(suspiciousUser);
	}
	public boolean validUserId(String me,List<Post>posts){

		Map<String, Object> body = new HashMap<>();
		body.put("userId",me);
		//check first if suspicious user exist in the database
//		final boolean auth_res = (boolean) amqpTemplate.convertSendAndReceive(
//				"authentication", body, m -> {
//					m.getMessageProperties().setHeader("command", "validateCommand");
//					m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);//reply queue
//					return m;
//				});
		//then check if he is blocked
		final List<String> res = (List<String>) amqpTemplate.convertSendAndReceive(
				"authentication", body, m -> {
					m.getMessageProperties().setHeader("command", "blockedByCommand");
					m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);//reply queue
					return m;
				});
		posts.removeIf(p -> res.contains(p.getUserId()));
		return true;
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
		validUserId(userId,post);
		return "DONE, Potatoes report post : "+(post);

	}
	
	// TAG
	public String assignModerator(String postId, String userId)throws Exception {
		
		if(postId == null)
			throw new Exception("Must include postId");
		if(userId == null)
			throw new Exception("Must include userId");
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		
		if(post == null)
			throw new Exception("This post does not exist");
		
		if(!post.getUserId().equals(userId)) 
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "access denied");
		
		Update update = new Update().set("moderatorId", userId);
		mongoOperations.updateFirst(query, update, Post.class);
		
		return "DONE, Potatoes report post : ";

	}
	
	public String checkPostReports(String postId, String userId)throws Exception {
		
		if(postId == null)
			throw new Exception("Must include postId");
		if(userId == null)
			throw new Exception("Must include userId");
		
		Query query = new Query();		
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		
		if(post == null)
			throw new Exception("This post does not exist");
		
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
	
	
	
	public List<Post> postRecommend(String userId) throws InterruptedException, ExecutionException, ResponseStatusException  {
    	
       //from the userId , if followed posts is empty , then we check top 10 most voted posted to add in list 
		// get the list of blocked users
//      amqpTemplate.convertAndSend("user_req",type_IDs,  m -> {
//      m.getMessageProperties().setHeader("command", "createNotificationCommand");
//      return m;
//  });	
		
		
		ArrayList<String> blockingUsers= new ArrayList<String>();
		
		
		Query query = new Query();
		query.with(Sort.by(Sort.Direction.DESC, "noOfFollwer"));
        System.out.println("kol btts kteer");
		List<Post> posts = mongoOperations.find(query.limit(30), Post.class, "post");
		List<Post> filteredPosts = new ArrayList<Post>();
		
		for(Post x: posts)
		{
			if(!blockingUsers.contains(x.getUserId()))
			{
				filteredPosts.add(x);
			}
		}
        
        System.out.println("kol btts kteer");
        
      
      
        return filteredPosts;
    }
	
	
	
//	public ResponseEntity addImage(String postId,String photoRef) throws Exception  {
//    	
//	      
//		Query query = new Query();
//		query.addCriteria(Criteria.where("_id").is(postId));
//		Post post = mongoOperations.findOne(query, Post.class, "post");
//		if(post!=null)
//		{
//			Update update = new Update().set("photoRef", photoRef);
//			try
//			{
//				mongoOperations.updateFirst(query, update, Post.class); 
//
//			}
//			catch (Exception e) 
//			{
//				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");
//			}
//			
//		}
//		else
//		{
//			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
//		}
//		 
//	        
//	      
//	      
//		return new ResponseEntity<>("Done Successfully", 
//				   HttpStatus.OK);
//	    }
	
	
	
}