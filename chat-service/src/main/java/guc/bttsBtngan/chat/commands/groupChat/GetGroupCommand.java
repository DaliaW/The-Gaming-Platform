package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;
import java.util.Map;

public class GetGroupCommand extends GroupChatCommand{

	@Override
	public void execute(HashMap<String, Object> map) {
		try {
			Map<String, Object> res = service.getGroup((String) map.get("id"));
		} catch(Exception e) {
			
		}
	}

	
}
