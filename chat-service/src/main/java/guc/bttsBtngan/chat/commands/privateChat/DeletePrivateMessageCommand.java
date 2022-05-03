package guc.bttsBtngan.chat.commands.privateChat;

import java.util.HashMap;

public class DeletePrivateMessageCommand extends PrivateChatCommand{
	
	@Override
	public void execute(HashMap<String, Object> body) {
		try {
			String res = service.deletePrivateMessage((String) body.get("user_id"), (String) body.get("private_id"), (String) body.get("message_id"));
			//TODO: message queues
		} catch (Exception  e) {
			//TODO:
		}
	}

}
