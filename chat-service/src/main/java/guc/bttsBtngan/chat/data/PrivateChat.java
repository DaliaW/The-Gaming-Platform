package guc.bttsBtngan.chat.data;

import java.util.List;

public class PrivateChat {

	private String user_1;
	private String user_2;
	
	public PrivateChat() {}
	
	public PrivateChat(String user_1, String user_2) {
		super();
		this.user_1 = user_1;
		this.user_2 = user_2;
	}

	public String getUser_1() {
		return user_1;
	}
	public void setUser_1(String user_1) {
		this.user_1 = user_1;
	}
	public String getUser_2() {
		return user_2;
	}
	public void setUser_2(String user_2) {
		this.user_2 = user_2;
	}

	@Override
	public String toString() {
		return "PrivateChat [user_1=" + user_1 + ", user_2=" + user_2 + "]";
	}
}
