package guc.bttsBtngan.chat.data;

public class ChatMessage {
	
	private String content;
	private String sender_id;
	private String timestamp;
	private String message_id;
	
	public ChatMessage() {}
	
	public ChatMessage(String content, String sender_id, String timestamp, String message_id) {
		super();
		this.content = content;
		this.sender_id = sender_id;
		this.timestamp = timestamp;
		this.message_id = message_id;
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
	public String gettimestamp() {
		return timestamp;
	}
	public void settimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ChatMessage [content=" + content + ", sender_id=" + sender_id + ", timestamp=" + timestamp + ", message_id=" + message_id + "]";
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}
	
}
