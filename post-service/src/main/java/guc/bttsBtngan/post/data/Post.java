package guc.bttsBtngan.post.data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;
import java.util.ArrayList;


@Document
public class Post {
	private String userId;
	private String content;
	private String photoRef;
	private Timestamp date;
	private String moderatorId;
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
		this.postTags = new ArrayList<String>();
		this.postFollowers = new ArrayList<String>();
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
	
	private class PostVotes{
		private String voterId;
		private boolean upVote;
		
		
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
	
	private class PostReports{
		private String userId;
		private String comment;
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
		
		
		
	}

	@Override
	public String toString() {
		return "Post [userId=" + userId + ", content=" + content + ", photoRef=" + photoRef + ", date=" + date
				+ ", moderatorId=" + moderatorId + "]";
	}
		
}
