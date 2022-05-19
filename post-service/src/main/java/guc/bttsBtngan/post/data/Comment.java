package guc.bttsBtngan.post.data;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

import org.springframework.data.annotation.Id;

public class Comment {
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Id
	private String id;
	private String content;
	private String commenterId;
	private ArrayList<CommentVote> commentVotes;
	private ArrayList<String> commentTags;
	
	
	public Comment() {}
	
	public Comment(String commenterId, String  content) {
		super();
		this.content = content;
		this.commenterId = commenterId;
		this.commentVotes = new ArrayList<CommentVote>();
		this.commentTags = new ArrayList<String>();
	}

	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCommenterId() {
		return commenterId;
	}
	
	public void setCommenterId(String commenterId) {
		this.commenterId = commenterId;
	}

	public ArrayList<CommentVote> getCommentVotes() {
		return commentVotes;
	}

	public void setCommentVotes(ArrayList<CommentVote> commentVotes) {
		this.commentVotes = commentVotes;
	}
	
	public void addCommentVote(CommentVote vote) {
		this.commentVotes.add(vote);
	}
	public void delCommentVote(CommentVote vote) {
		this.commentVotes.remove(vote);
	}

	public ArrayList<String> getCommentTags() {
		return commentTags;
	}

	public void setCommentTags(ArrayList<String> commentTags) {
		this.commentTags = commentTags;
	}

	public void addCommentTags(String tag) {
		this.commentTags.add(tag);
	}

	public void delCommentTags(String tag) {
		this.commentTags.remove(tag);
	}

	
	public static class CommentVote{
		private String voterId;
		private boolean upVote;
		
		public CommentVote(String voterId, boolean upVote) {
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
}
