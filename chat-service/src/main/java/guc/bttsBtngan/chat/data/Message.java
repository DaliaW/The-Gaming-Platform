package guc.bttsBtngan.chat.data;

import java.sql.Timestamp;

public class Message {
	
	private String content;
	private String sender_id;
	private Timestamp date;
	
	public Message() {}
	
	public Message(String content, String sender_id, Timestamp date) {
		super();
		this.content = content;
		this.sender_id = sender_id;
		this.date = date;
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
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Message [content=" + content + ", sender_id=" + sender_id + ", date=" + date + "]";
	}
	
}
