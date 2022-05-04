package guc.bttsBtngan.chat.commands.groupChat;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class ChangeGroupNameCommand extends GroupChatCommand{

	@Override
	public void execute(HashMap<String, Object> map) {
		try {
			String res = getService().changeGroupName((String)map.get("user_id"), (String)map.get("name"), (String)map.get("group_id"));
		} catch(Exception e) {
			
		}
	}

}
