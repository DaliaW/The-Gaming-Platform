package guc.bttsBtngan.chat.data;

import java.util.List;

public class GroupChat {
	
	private String name;
	private String admin_id;
	private List<String> participants;
	
	public GroupChat() {}
	
	public GroupChat(String document_id, String name, String admin_id, List<String> participants,
			List<Message> messages) {
		super();
		this.name = name;
		this.admin_id = admin_id;
		this.participants = participants;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getAdmin_id() {
		return admin_id;
	}
	public void setAdmin_id(String admin_id) {
		this.admin_id = admin_id;
	}
	public List<String> getParticipants() {
		return participants;
	}
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
	
	@Override
	public String toString() {
		return "GroupChat [name=" + name + ", admin_id=" + admin_id + ", participants=" + participants + "]";
	}

}
