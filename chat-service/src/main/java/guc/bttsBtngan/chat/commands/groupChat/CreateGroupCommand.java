package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import guc.bttsBtngan.chat.commands.Command;
import guc.bttsBtngan.chat.data.GroupChat;
import guc.bttsBtngan.chat.services.GroupChatService;

public class CreateGroupCommand extends GroupChatCommand {
	

	@Override
	public void execute(HashMap<String, Object> map) {
		GroupChat chat = new GroupChat();
		chat.setAdmin_id((String) map.get("admin_id"));
		chat.setName((String) map.get("name"));
		try {
			String res = service.createGroup(chat);
			//TODO: message queues
		} catch (Exception  e) {
			//TODO:
		}
	}

}
