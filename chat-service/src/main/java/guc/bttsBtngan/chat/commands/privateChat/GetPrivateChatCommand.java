package guc.bttsBtngan.chat.commands.privateChat;

import java.util.HashMap;

public class GetPrivateChatCommand extends PrivateChatCommand{
	
	@Override
	public void execute(HashMap<String, Object> body) {
		try {
			String res = service.getPrivateChat((String) body.get("user_id"), id);
			//TODO: message queues
		} catch (Exception  e) {
			//TODO:
		}
	}

}
