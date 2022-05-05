package guc.bttsBtngan.chat.data;

public class ChatMessage {
	
	private String content;
	private String sender_id;
	private String timestamp;
	
	public ChatMessage() {}
	
	public ChatMessage(String content, String sender_id, String timestamp) {
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
	public String gettimestamp() {
		return timestamp;
	}
	public void settimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "ChatMessage [content=" + content + ", sender_id=" + sender_id + ", timestamp=" + timestamp + "]";
	}
	
}
