package guc.bttsBtngan.chat.commands.privateChat;

import java.util.HashMap;

public class SendPrivateMessageCommand extends PrivateChatCommand{
	
	@Override
	public void execute(HashMap<String, Object> body) {
		try {
			String res = service.sendPrivateMessage((String) body.get("user_id"), (String) body.get("private_id"), (String) body.get("content"));
			//TODO: message queues
		} catch (Exception  e) {
			//TODO:
		}
	}

}
