package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Component;

import guc.bttsBtngan.chat.commands.Command;
import guc.bttsBtngan.chat.data.GroupChat;

@Component
public class CreateGroupCommand extends GroupChatCommand {
	
	@Override
	public Object execute(HashMap<String, Object> map) throws Exception {
		GroupChat chat = new GroupChat();
		chat.setAdmin_id((String) map.get("user_id"));
		chat.setName((String) map.get("name"));
		chat.setGroup_id(UUID.randomUUID().toString());
		return getService().createGroup(chat);
	}

}
