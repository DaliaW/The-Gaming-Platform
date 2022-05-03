package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;

public class SendGroupMessageCommand extends GroupChatCommand {

	@Override
	public void execute(HashMap<String, Object> map) {
		try {
			String res = service.sendGroupMessage((String) map.get("user_id"), (String) map.get("group_id"), (String) map.get("content"));
		} catch(Exception e) {
			
		}
	}

}
