package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;

public class JoinGroupCommand extends GroupChatCommand {

	@Override
	public void execute(HashMap<String, Object> map) {
		try {
			String res = service.joinGroup((String)map.get("user_id"), (String)map.get("group_id"));
		} catch(Exception e) {
			
		}
		
	}

}
