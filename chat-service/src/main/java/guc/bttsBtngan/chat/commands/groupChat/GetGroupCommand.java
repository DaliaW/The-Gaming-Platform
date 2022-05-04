package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class GetGroupCommand extends GroupChatCommand{

	@Override
	public Object execute(HashMap<String, Object> map) throws Exception {
		return getService().getGroup((String) map.get("id"));
	}
	
}
