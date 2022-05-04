package guc.bttsBtngan.chat.commands.privateChat;

import java.util.HashMap;
import java.util.Map;

public class GetPrivateChatCommand extends PrivateChatCommand{
	
	@Override
	public void execute(HashMap<String, Object> body) {
		try {
			Map<String, Object> res = service.getPrivateChat((String) body.get("user_id"), (String) body.get("private_id"));
			//TODO: message queues
		} catch (Exception  e) {
			//TODO:
		}
	}

}
