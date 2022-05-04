package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class LeaveGroupCommand extends GroupChatCommand{

	@Override
	public Object execute(HashMap<String, Object> map) throws Exception {
		return getService().leaveGroup((String)map.get("user_id"), (String)map.get("group_id"));
	}

}
