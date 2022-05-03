package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;

public class LeaveGroupCommand extends GroupChatCommand{

	@Override
	public void execute(HashMap<String, Object> map) {
		try {
			String res = service.leaveGroup((String)map.get("user_id"), (String)map.get("group_id"));
		} catch(Exception e) {
			
		}
	}

}
