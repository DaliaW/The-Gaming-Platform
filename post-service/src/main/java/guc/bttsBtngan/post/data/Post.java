package guc.bttsBtngan.post.data;
import org.springframework.data.mongodb.core.mapping.Document;

import guc.bttsBtngan.post.data.Comment.CommentVote;

import java.sql.Timestamp;
import java.util.ArrayList;


@Document
public class Post {
	private String userId;
	private String content;
	private String photoRef;
	private Timestamp date;
	private String moderatorId;
	private ArrayList<Comment> comments;
	private ArrayList<PostVote> postVotes;
	private ArrayList<PostReport> postReports;
	private ArrayList<String> postTags;
	private ArrayList<String> postFollowers;
	
	public Post() {}

	public Post(String userId, String content, String photoRef, Timestamp date
			, String moderatorId) {
		super();
		this.userId = userId;
		this.content = content;
		this.photoRef = photoRef;
		this.date = date;
		this.moderatorId = moderatorId;
		this.comments = new ArrayList<Comment>();
		this.postVotes = new ArrayList<PostVote>();
		this.postReports = new ArrayList<PostReport>();
		this.postTags = new ArrayList<String>();
		this.postFollowers = new ArrayList<String>();
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public ArrayList<PostVote> getPostVotes() {
		return postVotes;
	}
	public void addPostVote(PostVote vote) {
		this.postVotes.add(vote);
	}
	public void delPostVote(PostVote vote) {
		this.postVotes.remove(vote);
	}
	public void setPostVotes(ArrayList<PostVote> postVotes) {
		this.postVotes = postVotes;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPhotoRef() {
		return photoRef;
	}

	public void setPhotoRef(String photoRef) {
		this.photoRef = photoRef;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getModeratorId() {
		return moderatorId;
	}

	public void setModeratorId(String moderatorId) {
		this.moderatorId = moderatorId;
	}
	
	public ArrayList<String> getPostTags() {
		return postTags;
	}

	public void setPostTags(ArrayList<String> postTags) {
		this.postTags = postTags;
	}

	public void addPostTags(String userId) {
		this.postTags.add(userId);
	}

	public void delPostTags(String userId) {
		this.postTags.remove(userId);
	}

	public ArrayList<String> getPostFollowers() {
		return postFollowers;
	}
	
	public void setPostFollowers(ArrayList<String> postFollowers) {
		this.postFollowers = postFollowers;
	}
	
	public void addPostFollower(String userId) {
		this.postFollowers.add(userId);
	}

	public ArrayList<PostReport> getPostReports() {
		return postReports;
	}

	public void setPostReports(ArrayList<PostReport> postReports) {
		this.postReports = postReports;
	}
	
	public void addPostReport(PostReport report) {
		this.postReports.add(report);
	}
	
	public static class PostVote{
		private String voterId;
		private boolean upVote;
		
		public PostVote(String voterId, boolean upVote) {
			this.voterId = voterId;
			this.upVote = upVote;
		}
		
		public String getVoterId() {
			return voterId;
		}
		public void setVoterId(String voterId) {
			this.voterId = voterId;
		}
		public boolean isUpVote() {
			return upVote;
		}
		public void setUpVote(boolean upVote) {
			this.upVote = upVote;
		}
		
		
	}
	
	public static class PostReport{
		private String userId;
		private String comment;
		
		public PostReport(String userId, String comment) {
			this.userId = userId;
			this.comment = comment;
		}
		
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getComment() {
			return comment;
		}
		public void setComment(String comment) {
			this.comment = comment;
		}
		
		public String toString() {
			return "reporter: "+userId+" ---- content: "+comment;
		}
		
		
	}

	@Override
	public String toString() {
		return "Post [userId=" + userId + ", content=" + content + ", photoRef=" + photoRef + ", date=" + date
				+ ", moderatorId=" + moderatorId + "]"+
				"post followers: "+postFollowers+
				"reports: "+postReports;
	}
		
}
