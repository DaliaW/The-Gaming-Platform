package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;

public class UpdateMessageCommand extends GroupChatCommand{

	@Override
	public void execute(HashMap<String, Object> map) {
		try {
			String res = service.updateMessage((String) map.get("group_id"), (String) map.get("user_id"),
					(String) map.get("message_id"), (String) map.get("content"));
		} catch(Exception e) {
			
		}
	}

}
