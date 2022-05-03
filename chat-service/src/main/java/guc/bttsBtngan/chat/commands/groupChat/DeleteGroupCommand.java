package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;

public class DeleteGroupCommand extends GroupChatCommand{

	@Override
	public void execute(HashMap<String, Object> map) {
		try {
			String res = service.deleteGroup((String) map.get("group_id"), (String) map.get("user_id"));
		} catch(Exception e) {
			
		}
	}

}
