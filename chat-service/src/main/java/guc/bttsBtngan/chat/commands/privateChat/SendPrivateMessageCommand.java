package guc.bttsBtngan.chat.commands.privateChat;

import java.util.HashMap;

import org.springframework.stereotype.Component;

@Component
public class SendPrivateMessageCommand extends PrivateChatCommand{
	
	@Override
	public void execute(HashMap<String, Object> body) {
		try {
			String res = getService().sendPrivateMessage((String) body.get("user_id"), (String) body.get("private_id"), (String) body.get("content"));
			//TODO: message queues
		} catch (Exception  e) {
			//TODO:
		}
	}

}
