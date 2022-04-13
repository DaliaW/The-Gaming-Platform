package guc.bttsBtngan.post.data;

import java.util.ArrayList;

public class Comment {
	private String content;
	private String commenterId;
	private CommentVotes commentVotes;
	private ArrayList<String> commentTags;
	
	
	public Comment() {}
	
	public Comment(String content, String commenterId, CommentVotes commentVotes, ArrayList<String> commentTags) {
		super();
		this.content = content;
		this.commenterId = commenterId;
		this.commentVotes = commentVotes;
		this.commentTags = commentTags;
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

	public CommentVotes getCommentVotes() {
		return commentVotes;
	}

	public void setCommentVotes(CommentVotes commentVotes) {
		this.commentVotes = commentVotes;
	}

	public ArrayList<String> getCommentTags() {
		return commentTags;
	}

	public void setCommentTags(ArrayList<String> commentTags) {
		this.commentTags = commentTags;
	}



	private class CommentVotes{
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
}
