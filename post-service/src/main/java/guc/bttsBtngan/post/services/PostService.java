package guc.bttsBtngan.post.services;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;
import java.util.*;


import guc.bttsBtngan.post.amqp.RabbitMQConfig;
import guc.bttsBtngan.post.data.Comment;
import guc.bttsBtngan.post.data.Comment.CommentVote;
import guc.bttsBtngan.post.data.Post;
import guc.bttsBtngan.post.data.Post.PostVote;
import guc.bttsBtngan.post.data.Post.PostReport;
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
import com.mongodb.client.result.UpdateResult;

@Service
public class PostService {
	@Autowired
	FirebaseImageService firebaseImage;
    @Autowired
    MongoOperations mongoOperations;
	@Autowired
	private AmqpTemplate amqpTemplate;

	public String createPost(Post post) throws Exception {
		if(post.getUserId() == null)
		{
			throw new Exception("UserId required for creating post");
		}
		
		if(post.getContent() == null)
		{
			throw new Exception("Content required for creating post");
		}
		

		post.setDate(new Date(System.currentTimeMillis()) );
		
		post.setNoOfFollwer(0);
		
		post.setComments(new ArrayList<Comment>());
		
		post.setPostVotes(new ArrayList<PostVote>());
		
		post.setPostReports(new ArrayList<PostReport>());
		
		post.setPostTags(new ArrayList<String>());
		
		post.setPostFollowers(new ArrayList<String>());
		
		post.setBannedUsers(new ArrayList<String>());
		
        mongoOperations.save(post);
        
        HashMap<String, Object> type_ID= new HashMap<String, Object>();
        type_ID.put("type", "post");
        type_ID.put("user_id", post.getUserId());

       ArrayList<String> followers=(ArrayList<String>)amqpTemplate.convertSendAndReceive("user_req",type_ID,  m -> {
        m.getMessageProperties().setHeader("command", "getAllFollowersCommand");
        return m;
    });    
       if(followers!=null && followers.size()!=0)
       {
           
    	
           notifyFollowersOfPost(followers, "new post created by : "+post.getUserId());
           
       }
      
        return "DONE, created post is: "+(post).toString();
    }
    
	public String getValidPostId() throws Exception{
		Query query = new Query();
    	Post post = mongoOperations.findOne(query, Post.class, "post"); 
    	if(post==null)return "post is null";
    	return post.get_id().toString();
	}
	
	

    public Post getPost(String postId)throws Exception {
        Query query = new Query(Criteria.where("_id").is(postId));
        Post post = mongoOperations.findOne(query, Post.class, "post");

        if (post == null) {
            throw new Exception("post with id: " + postId + " is not found");
        }
        return post;
    }
    public String followPost(String userId, String postId, boolean follow)throws Exception {
    	Query query = new Query(Criteria.where("_id").is(postId));
    	Post post = mongoOperations.findOne(query, Post.class, "post");  	
    	
    	if(post == null)
    	{
    		throw new Exception("post with id: "+postId+" is not found");
    	}
    	
    	if(isBannedFromPost(post, userId)) {
    		throw new Exception("You are banned from this post");
    	}
    	
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
    	
        
      

        	ArrayList<String> postOwner=new ArrayList<String>();
        	postOwner.add(post.getUserId());
        	
             notifyFollowersOfPost(postOwner, "new follower followed your post : "+userId);
              
      
        
    	return "request done!";
    	
    }

    public String reportPost(String userId, String postId, String reportComment)throws Exception {
    	if(reportComment == null)
    	{
    		throw new Exception("reportComment is required");
    	}
    	
    	Query query = new Query(Criteria.where("_id").is(postId));
    	Post post = mongoOperations.findOne(query, Post.class, "post");
    	
    	if(post == null)
    	{
    		throw new Exception("post with id: "+postId+" is not found");
    	}
    	
    	if(isBannedFromPost(post, userId)) {
    		throw new Exception("You are banned from this post");
    	}
    	
    	Post.PostReport postReport = new Post.PostReport(userId, reportComment);

    	if(post.getPostReports() == null)
    	{
    		post.setPostReports(new ArrayList<Post.PostReport>());
    	}
    	post.addPostReport(postReport);
    	
    	Update update = new Update().set("postReports", post.getPostReports());
        mongoOperations.updateFirst(query, update, Post.class);

    	return "DONE, Potatoes report post";

    }

	void notifyFollowersOfPost(ArrayList<String>followers,String message){
        HashMap<String, Object> type_IDs= new HashMap<String, Object>();
	  	type_IDs.put("type", message);
	  	type_IDs.put("userIDs", followers);
        amqpTemplate.convertAndSend("notification_req",type_IDs,  m -> {
            m.getMessageProperties().setHeader("command", "createNotificationCommand");
            return m;
        });
	}
    public String commentPost(String userId, String postId, String comment)throws Exception {
    	if(comment == null)
    	{
    		throw new Exception("comment is required");
    	}
    	
    	Query query = new Query();
    	query.addCriteria(Criteria.where("_id").is(postId));
    	Post post = mongoOperations.findOne(query, Post.class, "post");
		if(post==null)throw new Exception("post id is not valid");
    	
		if(isBannedFromPost(post, userId)) {
    		throw new Exception("You are banned from this post");
    	}
		
		if(post.getComments()==null){
			post.setComments(new ArrayList<>());
		}
		
		int lastIdx;
		int commentsCount = post.getComments().size();
		if(commentsCount == 0)
		{
			lastIdx = -1;
		}
		else
		{
			Comment lastComment = post.getComments().get(commentsCount-1);
			lastIdx = Integer.parseInt(lastComment.getId());
		}
		
		Comment postComment = new Comment(userId, comment);
		postComment.setId(""+(lastIdx+1));
		
		
    	post.getComments().add(postComment);

    	
    	Update update = new Update().set("comments", post.getComments());
        mongoOperations.updateFirst(query, update, Post.class);
        ArrayList<String> usersToBeNotified=new ArrayList<String>();
        usersToBeNotified.addAll(post.getPostFollowers());
        usersToBeNotified.add(post.getUserId());
        notifyFollowersOfPost(usersToBeNotified, "new comment");
    	return "DONE, Potatoes comment post";

    }
    
 
    
    public String commentVote(String userId, String postId, String commentId,boolean vote)throws Exception {
    	if(commentId == null)
    	{
    		throw new Exception("commentId is required");
    	}
    	
    	Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		if(post==null){
			throw new Exception("postId is not valid");
		}
		
		if(isBannedFromPost(post, userId)) {
    		throw new Exception("You are banned from this post");
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
				break;
			}
		}
		if(!found) {
			CommentVote cv=new CommentVote(userId,vote);
			cmnt.addCommentVote(cv);
		}
		
    	Update update = new Update().set("comments", post.getComments());
        mongoOperations.updateFirst(query, update, Post.class);
       
        ArrayList<String> usersToBeNotified=new ArrayList<String>();
       
        usersToBeNotified.add(cmnt.getCommenterId());
        notifyFollowersOfPost(usersToBeNotified, "new voting comment ");
		return "DONE, Potatoes vote in comment";

	}
    
    
    public String postVote(String userId, String postId, boolean vote)throws Exception {
    	
  		Query query = new Query();
  		query.addCriteria(Criteria.where("_id").is(postId));
  		Post post = mongoOperations.findOne(query, Post.class, "post");
  		if(post==null){
			throw new Exception("postId is not valid");
  		}
  		
  		if(isBannedFromPost(post, userId)) {
    		throw new Exception("You are banned from this post");
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
  				break;
  			}
  		}
  		if(!found) {
  			PostVote pv=new PostVote(userId,vote);
  			post.addPostVote(pv);
  		}
  		
  		

    	Update update = new Update().set("postVotes", post.getPostVotes());
        mongoOperations.updateFirst(query, update, Post.class);
        ArrayList<String> usersToBeNotified=new ArrayList<String>();
        usersToBeNotified.add(post.getUserId());
        notifyFollowersOfPost(usersToBeNotified, "new vote on post");
  		return "DONE, Potatoes";

  	}
	public String tagInPost(String postId, String[]userIds,String userIdSending)throws Exception {
		 
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		
			
			
		if(post==null){
			throw new Exception("post id is not valid");
		}
		if(!userIdSending.equals(post.getUserId())) {
			throw new Exception("ya kalb");
		}
		for(String userId:userIds){
			if(!validateUserId(userIdSending,userId) || isBannedFromPost(post, userId)){
				throw new Exception("User with user id "+userId+" is not a valid user or is banned");
			}
			post.addPostTags(userId);
		}
		Update update = new Update().set("postTags", post.getPostTags());
		mongoOperations.updateFirst(query, update, Post.class);
		
		ArrayList<String> usersToBeNotified=new ArrayList<String>();
		for(String str:userIds) {
			usersToBeNotified.add(str);
		}
	        notifyFollowersOfPost(usersToBeNotified, "you are tagged in a new post made by user : "+post.getUserId());
		return "DONE, Potatoes tag in post : "+(post).toString();

	}

	public String delTagInPost(String postId, String[]userIds, String userIdSending)throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		if(post==null){
			throw new Exception("post id is not valid");
		}
		if(!userIdSending.equals(post.getUserId())) {
			throw new Exception("ya kalb");
		}
		for(String userId:userIds){
			if(!post.getPostTags().contains(userId)){
				throw new Exception("User with user id "+userId+" is not tagged already in the post");
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
		int idx=-1;
		for(int i=0;i<post.getComments().size();i++){
			Comment c=post.getComments().get(i);
			if(c.getId().equals(commentId)){
				cmnt=c;
				idx=i;
			}
		}
		if(cmnt==null){
			throw new Exception("comment id is not valid");
		}
		if(!userIdSending.equals(cmnt.getCommenterId())) {
			throw new Exception("ya kalb");
		}
		for(String userId:userIds){
			if(!validateUserId(userIdSending,userId) || isBannedFromPost(post, userId)){
				throw new Exception("User with user id "+userId+" is not a valid user or is banned");
			}
			cmnt.addCommentTags(userId);
		}
		post.getComments().set(idx,cmnt);

		Update update = new Update().set("comments", post.getComments());
		mongoOperations.updateFirst(query, update, Post.class);

		ArrayList<String> usersToBeNotified=new ArrayList<String>();
		for(String str:userIds) {
			usersToBeNotified.add(str);
		}
	        notifyFollowersOfPost(usersToBeNotified, "you are tagged in a  comment made by user : "+cmnt.getCommenterId());
		
		
             
		
		return "DONE, Potatoes tag in post : "+(post).toString();

	}

	public String delCommentTagInPost(String postId, String commentId, String[]userIds, String userIdSending)throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		if(post==null){
			throw new Exception("post id is not valid");
		}
		Comment cmnt=null;
		int idx=-1;
		for(int i=0;i<post.getComments().size();i++){
			Comment c=post.getComments().get(i);
			if(c.getId().equals(commentId)){
				cmnt=c;
				idx=i;
			}
		}
		if(cmnt==null){
			throw new Exception("comment id is not valid");
		}
		if(!userIdSending.equals(cmnt.getCommenterId())) {
			throw new Exception("ya kalb");
		}
		for(String userId:userIds){
			if(!cmnt.getCommentTags().contains(userId)){
				new Exception("User with user id "+userId+" is not tagged already in the comment");
			}
			cmnt.delCommentTags(userId);
		}
		post.getComments().set(idx,cmnt);

		Update update = new Update().set("comments", post.getComments());
		mongoOperations.updateFirst(query, update, Post.class);

		return "DONE, Potatoes tag in post : "+(post).toString();

	}
	public boolean validateUserId(String me,String suspiciousUser){

		Map<String, Object> body = new HashMap<>();
		body.put("user_id",me);
		//check first if suspicious user exist in the database
//		final boolean auth_res = (boolean) amqpTemplate.convertSendAndReceive(
//				"authentication", body, m -> {
//					m.getMessageProperties().setHeader("command", "validateCommand");
//					m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);//reply queue
//					return m;
//				});
		//then check if he is blocked
		final List<String> res = (List<String>) amqpTemplate.convertSendAndReceive(
				"user_req", body, m -> {
					m.getMessageProperties().setHeader("command", "blockedByCommand");
					m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);//reply queue
					return m;
				});
		return true && !res.contains(suspiciousUser);
	}
	public boolean validUserId(String me,List<Post>posts){

		Map<String, Object> body = new HashMap<>();
		body.put("user_id",me);
		//check first if suspicious user exist in the database
//		final boolean auth_res = (boolean) amqpTemplate.convertSendAndReceive(
//				"authentication", body, m -> {
//					m.getMessageProperties().setHeader("command", "validateCommand");
//					m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);//reply queue
//					return m;
//				});
		//then check if he is blocked
		final List<String> res = (List<String>) amqpTemplate.convertSendAndReceive(
				"user_req", body, m -> {
					m.getMessageProperties().setHeader("command", "blockedByCommand");
					m.getMessageProperties().setReplyTo(RabbitMQConfig.reply_queue);//reply queue
					return m;
				});
		posts.removeIf(p -> res!=null && res.contains(p.getUserId()));
		return true;
	}
	public String searchPosts(String subContent,String userId) throws Exception {
		Query query = new Query();
		StringBuilder pattern= new StringBuilder(".*");//starts with anything
		String[]tokens=subContent.split(" ");
		for(String s:tokens){
			pattern.append("(?i)").append(s);//case-insensitive
			pattern.append(".*");//followed by anything
		}
		query.addCriteria(Criteria.where("content").regex(pattern.toString()));
		List<Post> post = mongoOperations.find(query, Post.class, "post");

		post.removeIf(p -> (!validateUserId(userId,p.getUserId()) || isBannedFromPost(p, userId)));

		return post.toString();

//		validUserId(userId,post);
//		return "DONE, Potatoes report post : "+(post);

	}
	
	// TAG
	public String assignModerator(String postId, String userId, String modId)throws Exception {
		
		if(postId == null)
			throw new Exception("Must include postId");
		if(userId == null)
			throw new Exception("Must include userId");
		if(modId == null)
			throw new Exception("Must include userId");
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(postId));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		
		if(post == null)
			throw new Exception("This post does not exist");
		
		if(!post.getUserId().equals(userId)) 
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "access denied");
		
		Update update = new Update().set("moderatorId", modId);
		mongoOperations.updateFirst(query, update, Post.class);
		
		return "DONE, Potatoes moderator";

	}
	
	public String moderatorBanUser(String modId, String postId, String userId, boolean ban)throws Exception {
    	
  		Query query = new Query();
  		query.addCriteria(Criteria.where("_id").is(postId));
  		Post post = mongoOperations.findOne(query, Post.class, "post");
  		
  		if(post==null){
			throw new Exception("postId is not valid");
  		}
  		
  		if(!post.getModeratorId().equals(modId)) {
  			throw new Exception("msh postak");
  		}
  		
  		boolean found = false;
  		for(String bu: post.getBannedUsers() ) {
  			if(bu.equals(userId)) {
  				found =true;
  				if(!ban) 
  					post.getBannedUsers().remove(userId);
  				break;
  			}
  		}
  		if(!found && ban) {
  			post.getBannedUsers().add(userId);
  			post.getPostFollowers().remove(userId);
  			post.setNoOfFollwer(post.getPostFollowers().size());
  		}
  		
  		

    	Update update = new Update()
    			.set("bannedUsers", post.getBannedUsers())
				.set("postFollowers", post.getPostFollowers())
				.set("noOfFollwer", post.getNoOfFollwer());
		
        mongoOperations.updateFirst(query, update, Post.class);
        
  		return "DONE, Potatoes";

  	}
	
	public boolean isBannedFromPost(Post post, String userId){
  		
  		if(post.getBannedUsers().contains(userId))
  			return true;
  		else
  			return false;
	}
	
	public ArrayList<PostReport> checkPostReports(String postId, String userId)throws Exception {
		
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

		return post.getPostReports();

	}

	// !!!! ama nshoof !!!!
	
	public String attachImageToPost(String userId,String post_id,  MultipartFile photo) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(post_id));
		Post post = mongoOperations.findOne(query, Post.class, "post");
		
		if(post==null){
			throw new Exception("Post does not exist");
		}
		
		if(photo==null){
			throw new Exception("Photo is corrupted or does not exist");
		}
		
		if(post.getUserId()!= userId) {
			throw new Exception("msh postak");
		}
		
		
		String textPath=firebaseImage.save(photo);
		Update update = new Update().set("photoRef", textPath);
	    mongoOperations.updateFirst(query, update, Post.class);
		return "Done!";
}
	
	// !!!! shofna !!!!
	
	
	
	public List<Post> postRecommend(String userId) throws Exception  {
        

	      HashMap<String, Object> type_IDs= new HashMap<String, Object>();
	      type_IDs.put("type", "post");
	      type_IDs.put("user_id", userId);

	     ArrayList<String> blockingUsers=(ArrayList<String>)amqpTemplate.convertSendAndReceive("user_req",type_IDs,  m -> {
	      m.getMessageProperties().setHeader("command", "blockedByCommand");
	      return m;
	  });    
	        
	     
	     
	        Query query = new Query();
	        query.with(Sort.by(Sort.Direction.DESC, "noOfFollwer"));
	        List<Post> posts = mongoOperations.find(query.limit(30), Post.class, "post");
	        List<Post> filteredPosts = new ArrayList<Post>();
	        if(blockingUsers!=null && blockingUsers.size()!=0 && posts!=null && posts.size()!=0)
	        {
	        	for(Post x: posts)
		        {
		            if(!blockingUsers.contains(x.getUserId()))
		            {
		                filteredPosts.add(x);
		            }
		        }
	        }
	        
	      
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