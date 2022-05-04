package guc.bttsBtngan.chat.data;

import com.google.cloud.firestore.annotation.ServerTimestamp;
import com.google.cloud.Timestamp;

public class Message {
	
	private String content;
	private String sender_id;
	@ServerTimestamp
	private Timestamp timestamp;
	
	public Message() {}
	
	public Message(String content, String sender_id, Timestamp timestamp) {
		super();
		this.content = content;
		this.sender_id = sender_id;
		this.timestamp = timestamp;
	}

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public Timestamp gettimestamp() {
		return timestamp;
	}
	public void settimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + ", sender_id=" + sender_id + ", timestamp=" + timestamp + "]";
	}
	
}
