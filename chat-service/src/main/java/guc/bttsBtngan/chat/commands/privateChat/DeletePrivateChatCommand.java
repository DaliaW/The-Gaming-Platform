package guc.bttsBtngan.chat.commands.privateChat;

import java.util.HashMap;

public class DeletePrivateChatCommand extends PrivateChatCommand{
	
	@Override
	public void execute(HashMap<String, Object> body) {
		try {
			String res = service.deletePrivateChat((String) body.get("user_id"), (String) body.get("private_id"));
			//TODO: message queues
		} catch (Exception  e) {
			//TODO:
		}
	}

}
