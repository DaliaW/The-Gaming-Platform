package guc.bttsBtngan.chat.commands.privateChat;

import java.util.HashMap;

import guc.bttsBtngan.chat.data.PrivateChat;

public class CreatePrivateChatCommand extends PrivateChatCommand {
	

	@Override
	public void execute(HashMap<String, Object> map) {
		PrivateChat chat = new PrivateChat();
		chat.setUser_1((String) map.get("user_1"));
		chat.setUser_2((String) map.get("user_2"));
		try {
			String res = service.createPrivateChat(chat);
			//TODO: message queues
		} catch (Exception  e) {
			//TODO:
		}
	}

}
